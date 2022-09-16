package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.ContractAttachment;
import com.realfinance.sofa.cg.core.domain.PurchaseResultNotice;
import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import com.realfinance.sofa.cg.core.facade.CgContractManageFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.ContractManageRepository;
import com.realfinance.sofa.cg.core.repository.PurchaseResultNoticeRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.*;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;


@Service
@SofaService(interfaceType = CgContractManageFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgContractManageImpl implements CgContractManageFacade {
    private static final Logger log = LoggerFactory.getLogger(CgContractManageImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final ContractManageRepository contractManageRepository;
    private final ContractManageMapper contractManageMapper;
    private final ContractManageDetailsMapper contractManageDetailsMapper;
    private final ContractManageSaveMapper contractManageSaveMapper;
    private final PurchaseResultNoticeRepository purchaseResultNoticeRepository;

    public CgContractManageImpl(JpaQueryHelper jpaQueryHelper, ContractManageRepository contractManageRepository, ContractManageMapper contractManageMapper, ContractManageDetailsMapper contractManageDetailsMapper, ContractManageSaveMapper contractManageSaveMapper, PurchaseResultNoticeRepository purchaseResultNoticeRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.contractManageRepository = contractManageRepository;
        this.contractManageMapper = contractManageMapper;
        this.contractManageDetailsMapper = contractManageDetailsMapper;
        this.contractManageSaveMapper = contractManageSaveMapper;
        this.purchaseResultNoticeRepository = purchaseResultNoticeRepository;
    }

    @Override
    public Page<CgContractManageDto> list(CgContractManageQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<ContractManage> result = contractManageRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(contractManageMapper::toDto);
    }

    @Override
    public CgContractManageDetailsDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return contractManageDetailsMapper.toDto(getContractManage(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(@NotNull CgContractManageSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        ContractManage contractManage;
        if (saveDto.getId() == null) { // 新增
            contractManage = contractManageSaveMapper.toEntity(saveDto);
            List<ContractAttachment> contractAttachments = contractManage.getContractAttachments();
            if (contractAttachments != null) {
                for (ContractAttachment contractAttachment : contractAttachments) {
                    contractAttachment.setSource("合同管理");
                    contractAttachment.setUploader(DataScopeUtils.loadPrincipalId().orElse(null));
                }
            }
            contractManage.setContractAttachments(contractAttachments);
            contractManage.setTenantId(DataScopeUtils.loadTenantId());
            contractManage.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
            contractManage.setFileStatus(0);
        } else { // 修改
            ContractManage entity = getContractManage(saveDto.getId());
            contractManage = contractManageSaveMapper.updateEntity(entity, saveDto);
        }
        try {
            //重置 预计通知待办发送状态
            contractManage.setExpireStatus(0);
            ContractManage saved = contractManageRepository.saveAndFlush(contractManage);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败,because:" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<ContractManage> toDelete = contractManageRepository.findAll(
                ((Specification<ContractManage>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 删除
        try {
            contractManageRepository.deleteAll(toDelete);
            contractManageRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败", e);
            }
            throw businessException("删除失败");
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFileStatus(Integer id) {
        ContractManage contractManage = contractManageRepository.findById(id).get();
        contractManage.setFileStatus(1);
        try {
            ContractManage saved = contractManageRepository.saveAndFlush(contractManage);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败,becauser:" + e.getMessage());
        }
    }


    @Override
    public List<CgContractManageDto> listToTask(Long date) {
        Long b = date * 30 * 24 * 3600 * 1000;
        //获取当前时间戳
        Long nowTime = System.currentTimeMillis();
        List<ContractManage> list = contractManageRepository.findByExpireStatus(0);
        if (list.size() == 0) {
            return null;
        }
        List<ContractManage> manages = new ArrayList<>();
        for (ContractManage contractManage : list) {
            LocalDate expireDate = contractManage.getExpireDate();
            Long contractManageDate = expireDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
            if (contractManageDate > nowTime) {
                System.out.println(contractManageDate - nowTime);
                System.out.println(b);
                if ((contractManageDate - nowTime) <= b) {
                    manages.add(contractManage);
                }
            }
        }
        List<CgContractManageDto> dtoList = contractManageMapper.toDtoList(manages);

        return dtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateExpireStatus(List<Integer> ids, Integer expireStatus) {
        List<ContractManage> list = contractManageRepository.findAllById(ids);
        for (ContractManage contractManage : list) {
            contractManage.setExpireStatus(expireStatus);
        }
        try {
            contractManageRepository.saveAll(list);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败,becauser:" + e.getMessage());
        }
    }

    @Override
    public List<CgContractManageDto> listToSupplierTask(Long date) {
        Long b = date * 30 * 24 * 3600 * 1000;
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        //获取当前0点的整点时间戳
        Long nowTime = null;
        try {
            nowTime = s.parse(s.format(new Date())).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<ContractManage> list = contractManageRepository.findAll();
        List<PurchaseResultNotice> purchaseResultNoticeList = purchaseResultNoticeRepository.findByPassTimeNotNull();
        if (list.size() == 0 || purchaseResultNoticeList.size() == 0) {
            return null;
        }
        //根据key: projectId 和 value:passTime 生成Map集合
        Map<Integer, LocalDateTime> map = purchaseResultNoticeList.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e.getPassTime()));

        List<ContractManage> manages = new ArrayList<>();
        for (ContractManage contractManage : list) {
            LocalDateTime passTime = map.get(contractManage.getProject().getId());
            if (passTime != null) {
                //结果通过时间(只取年月日)
                Long purchaseTime = null;
                try {
                    purchaseTime = s.parse(s.format(passTime)).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (nowTime > purchaseTime) {
                    if ((nowTime - purchaseTime) % b == 0) {
                        manages.add(contractManage);
                    }
                }
            }
        }
        List<CgContractManageDto> dtoList = contractManageMapper.toDtoList(manages);

        return dtoList;
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected ContractManage getContractManage(Integer id) {
        Objects.requireNonNull(id);
        List<ContractManage> all = contractManageRepository.findAll(
                ((Specification<ContractManage>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!contractManageRepository.existsById(id)) {
                System.out.println("找不到相应合同管理");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }
}
