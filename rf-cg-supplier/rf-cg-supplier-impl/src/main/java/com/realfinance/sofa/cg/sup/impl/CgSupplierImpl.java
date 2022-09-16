package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.rpc.common.utils.BeanUtils;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.Supplier;
import com.realfinance.sofa.cg.sup.domain.SupplierAccount;
import com.realfinance.sofa.cg.sup.domain.SupplierCredit;
import com.realfinance.sofa.cg.sup.domain.SupplierLabel;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.sup.repository.SupplierAccountRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierCreditRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierLabelRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierDetailsMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierSmallMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import com.realfinance.sofa.sdebank.SdebankEquityPenetration;
import com.realfinance.sofa.sdebank.model.CreditDto;
import com.realfinance.sofa.sdebank.model.EquityPenetrationDto;
import com.realfinance.sofa.sdebank.response.TokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierImpl implements CgSupplierFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupplierImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierAccountRepository supplierAccountRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierLabelRepository supplierLabelRepository;
    private final SupplierMapper supplierMapper;
    private final SupplierSmallMapper supplierSmallMapper;
    private final SupplierDetailsMapper supplierDetailsMapper;
    private final SupplierCreditRepository supplierCreditRepository;

    public CgSupplierImpl(JpaQueryHelper jpaQueryHelper,
                          SupplierAccountRepository supplierAccountRepository,
                          SupplierRepository supplierRepository,
                          SupplierLabelRepository supplierLabelRepository,
                          SupplierMapper supplierMapper,
                          SupplierSmallMapper supplierSmallMapper,
                          SupplierDetailsMapper supplierDetailsMapper, SupplierCreditRepository supplierCreditRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.supplierAccountRepository = supplierAccountRepository;
        this.supplierRepository = supplierRepository;
        this.supplierLabelRepository = supplierLabelRepository;
        this.supplierMapper = supplierMapper;
        this.supplierSmallMapper = supplierSmallMapper;
        this.supplierDetailsMapper = supplierDetailsMapper;
        this.supplierCreditRepository = supplierCreditRepository;
    }

    @Override
    public Page<CgSupplierDto> list(CgSupplierQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<Supplier> result = supplierRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierMapper::toDto);
    }

    @Override
    public CgSupplierDto getById(Integer id) {
        Objects.requireNonNull(id);
        return supplierMapper.toDto(getSupplier(id));
    }

    @Override
    public CgSupplierDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        return supplierDetailsMapper.toDto(getSupplier(id));
    }

    @Override
    public CgSupplierDetailsDto getDetailsByUsername(String username) {
        Objects.requireNonNull(username);
        String tenantId = DataScopeUtils.loadTenantId();
        SupplierAccount account = supplierAccountRepository.findByTenantIdAndUsername(tenantId, username)
                .orElseThrow(() -> entityNotFound(SupplierAccount.class, "username", username));
        Supplier supplier = account.getSupplier();
        if (supplier == null) {
            return null;
        }
        return supplierDetailsMapper.toDto(supplier);
    }

    @Override
    public Page<CgSupplierSmallDto> queryRefer(CgSupplierQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<Supplier> result = supplierRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierSmallMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSupplierLabels(Integer id, Set<Integer> supplierLabelIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(supplierLabelIds);
        if (supplierLabelIds.isEmpty()) {
            return;
        }
        Supplier supplier = getSupplier(id);
        String tenantId = supplier.getTenantId();
        Set<SupplierLabel> supplierLabels = supplier.getSupplierLabels();
        supplierLabelRepository.findAllById(supplierLabelIds).forEach(e -> {
            if (!StringUtils.equals(tenantId, e.getTenantId())) {
                throw businessException("关联标签失败，TenantId不匹配");
            }
            supplierLabels.add(e);
        });

        try {
            supplierRepository.saveAndFlush(supplier);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存标签关联失败", e);
            }
            throw businessException("保存标签关联失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeSupplierLabels(Integer id, Set<Integer> supplierLabelIds) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(supplierLabelIds);
        Supplier supplier = getSupplier(id);
        Set<SupplierLabel> supplierLabels = supplier.getSupplierLabels();
        supplierLabels.removeIf(s -> supplierLabelIds.contains(s.getId()));
        try {
            supplierRepository.saveAndFlush(supplier);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除关联标签失败", e);
            }
            throw businessException("删除关联标签失败");
        }
    }

    @Override
    public boolean matchesSupplierLabel(@NotNull Collection<Integer> supplierIds, @NotNull Integer supplierLabelId) {
        Objects.requireNonNull(supplierIds);
        Objects.requireNonNull(supplierLabelId);
        if (supplierIds.isEmpty()) {
            return false;
        }
        SupplierLabel supplierLabel = supplierLabelRepository.getOne(supplierLabelId);
        HashSet<Integer> idSet = new HashSet<>(supplierIds);
        int count = supplierRepository.countByIdInAndSupplierLabels(idSet, supplierLabel);
        return count == idSet.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgCreditDto> updateSupplierCredit(List<CgCreditDto> dtos) {
        //查询供应商库数据
        List<Integer> ids = dtos.stream().map(e -> e.getSupplier().getId()).collect(Collectors.toUnmodifiableList());
        List<Supplier> suppliers = supplierRepository.findAllById(ids);
        Map<Integer, Supplier> map = suppliers.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        //查询供应商库子表-有数据先清空
        List<SupplierCredit> supplierCredits = supplierCreditRepository.findBySupplierIsIn(suppliers);
        if (supplierCredits.size() != 0) {
            List<Integer> idList = supplierCredits.stream().map(SupplierCredit::getId).collect(Collectors.toUnmodifiableList());
            supplierCreditRepository.deleteByIdIn(idList);
        }
        SdebankEquityPenetration sd = new SdebankEquityPenetration();
        TokenResponse token = sd.getToken();
        List<CreditDto> creditDtoList = new ArrayList<>();
        for (CgCreditDto dto : dtos) {
            EquityPenetrationDto equityPenetrationDto = new EquityPenetrationDto();
            equityPenetrationDto.setSupplierId(dto.getSupplier().getId());
            equityPenetrationDto.setName(dto.getSupplier().getName());
            equityPenetrationDto.setUnifiedSocialCreditCode(dto.getSupplier().getUnifiedSocialCreditCode());
            List<CreditDto> creditDtos = sd.raExCompanyFxMx(token, equityPenetrationDto);
            if (creditDtos.size() == 0) {
                dto.setSupplierCreditStatus("0");
            } else {
                dto.setSupplierCreditStatus("1");
            }
            creditDtoList.addAll(creditDtos);
        }
        if (creditDtoList.size() != 0) {
            List<SupplierCredit> list = new ArrayList<>();
            for (CreditDto creditDto : creditDtoList) {
                SupplierCredit supplierCredit = getSupplierCredit(creditDto);
                supplierCredit.setSupplier(map.get(creditDto.getSupplierId()));
                list.add(supplierCredit);
            }
            //更新数据
            try {
                supplierCreditRepository.saveAll(list);
            } catch (Exception e) {
                log.error("供应商信用信息表更新失败!");
            }
        }
        return dtos;
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected Supplier getSupplier(Integer id) {
        Objects.requireNonNull(id);
        List<Supplier> all = supplierRepository.findAll(
                ((Specification<Supplier>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!supplierRepository.existsById(id)) {
                throw entityNotFound(Supplier.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    protected SupplierCredit getSupplierCredit(CreditDto dto) {
        SupplierCredit supplierCredit = new SupplierCredit();
        supplierCredit.setDocumentNumbers(getString(dto.getDocumentNumbers()));
        supplierCredit.setPenaltyItemNames(getString(dto.getPenaltyItemNames()));
        supplierCredit.setPenaltyCauses(getString(dto.getPenaltyCauses()));
        supplierCredit.setPenaltyDates(getString(dto.getPenaltyDates()));
        supplierCredit.setPenaltyType(dto.getPenaltyType());
        return supplierCredit;
    }

    /**
     * list集合转String
     * @param list
     * @return
     */
    protected String getString(Collection list){
        if (list==null ||list.size()==0){
            return null;
        }
        String str = StringUtils.substringBeforeLast(
                StringUtils.substringAfterLast(list.toString(), "["), "]");
        return str;
    }
}
