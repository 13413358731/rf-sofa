package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.*;
import com.realfinance.sofa.cg.sup.facade.CgSupplierExaminationFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDetailsSaveDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierExaminationQueryCriteria;
import com.realfinance.sofa.cg.sup.repository.SupplierAccountRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierExaminationCreditRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierExaminationRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierExaminationDetailsMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierExaminationDetailsSaveMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierExaminationMapper;
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
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;

import static com.realfinance.sofa.cg.sup.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.sup.domain.SupplierAttachmentCategory.*;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgSupplierExaminationFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierExaminationImpl implements CgSupplierExaminationFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupplierExaminationImpl.class);

    // 必要的附件
    private static final SupplierAttachmentCategory[] NECESSARY_ATTACHMENT_CATEGORY = {FINANCIAL_REPORT,BUSINESS_LICENSE,QUALIFICATION_CERTIFICATE,LEASE_CONTRACT,MAIN_BUSINESS,ID_CARD_FRONT,ID_CARD_BACK,};

    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierAccountRepository supplierAccountRepository;
    private final SupplierExaminationRepository supplierExaminationRepository;
    private final SupplierRepository supplierRepository;
    private final SupplierExaminationMapper supplierExaminationMapper;
    private final SupplierExaminationDetailsMapper supplierExaminationDetailsMapper;
    private final SupplierExaminationDetailsSaveMapper supplierExaminationDetailsSaveMapper;
    private final SupplierExaminationCreditRepository supplierExaminationCreditRepository;

    public CgSupplierExaminationImpl(JpaQueryHelper jpaQueryHelper,
                                     SupplierAccountRepository supplierAccountRepository,
                                     SupplierExaminationRepository supplierExaminationRepository,
                                     SupplierRepository supplierRepository,
                                     SupplierExaminationMapper supplierExaminationMapper,
                                     SupplierExaminationDetailsMapper supplierExaminationDetailsMapper,
                                     SupplierExaminationDetailsSaveMapper supplierExaminationDetailsSaveMapper, SupplierExaminationCreditRepository supplierExaminationCreditRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.supplierAccountRepository = supplierAccountRepository;
        this.supplierExaminationRepository = supplierExaminationRepository;
        this.supplierRepository = supplierRepository;
        this.supplierExaminationMapper = supplierExaminationMapper;
        this.supplierExaminationDetailsMapper = supplierExaminationDetailsMapper;
        this.supplierExaminationDetailsSaveMapper = supplierExaminationDetailsSaveMapper;
        this.supplierExaminationCreditRepository = supplierExaminationCreditRepository;
    }

    @Override
    public Page<CgSupplierExaminationDto> list(CgSupplierExaminationQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierExamination> result = supplierExaminationRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierExaminationMapper::toDto);
    }

    @Override
    public CgSupplierExaminationDto getById(Integer id) {
        Objects.requireNonNull(id);
        List<SupplierExamination> all = supplierExaminationRepository.findAll(((Specification<SupplierExamination>) (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), id);
        }).and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!supplierExaminationRepository.existsById(id)) {
                throw entityNotFound(SupplierExamination.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return supplierExaminationMapper.toDto(all.get(0));
    }

    @Override
    public CgSupplierExaminationDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        List<SupplierExamination> all = supplierExaminationRepository.findAll(((Specification<SupplierExamination>) (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), id);
        }).and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!supplierExaminationRepository.existsById(id)) {
                throw entityNotFound(SupplierExamination.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return supplierExaminationDetailsMapper.toDto(all.get(0));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createFromInternal(Integer supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> entityNotFound(Supplier.class, "id", supplierId));
        SupplierExamination supplierExamination = supplierExaminationMapper.fromSupplier(supplier);
        supplierExamination.setCategory(SupplierExamination.Category.MODIFY_FROM_INTERNAL);
        supplierExamination.setStatus(FlowStatus.EDIT);
        preSave(supplierExamination);
        try {
            SupplierExamination saved = supplierExaminationRepository.saveAndFlush(supplierExamination);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("创建失败",e);
            }
            throw businessException("创建失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgSupplierExaminationDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);

        SupplierAccount supplierAccount = supplierAccountRepository.findById(saveDto.getAccount())
                .orElseThrow(() -> businessException("缺少供应商账号信息"));
        supplierAccountRepository.lockAccount(supplierAccount.getId());

        if (supplierAccount.getSupplier() == null) {
            saveDto.setCategory(SupplierExamination.Category.INITIAL.name());
        }

        SupplierExamination supplierExamination;
        if (saveDto.getId() == null) { // 新增
            supplierExamination = supplierExaminationDetailsSaveMapper.toEntity(saveDto);
            supplierExamination.setTenantId(DataScopeUtils.loadTenantId());
            supplierExamination.setStatus(FlowStatus.EDIT);
        } else { // 修改
            SupplierExamination entity = supplierExaminationRepository.findById(saveDto.getId())
                    .orElseThrow(() -> entityNotFound(SupplierExamination.class, "id", saveDto.getId()));
            supplierExamination = supplierExaminationDetailsSaveMapper.updateEntity(entity,saveDto);
        }
        preSave(supplierExamination);
        try {
            SupplierExamination saved = supplierExaminationRepository.saveAndFlush(supplierExamination);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("创建审核失败",e);
            }
            throw businessException("创建审核失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status, String reason) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);

        SupplierExamination supplierExamination = supplierExaminationRepository.findById(id)
                .orElseThrow(() -> entityNotFound(SupplierExamination.class,"id",id));

        FlowStatus currentStatusEnum = supplierExamination.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}，reason：{}",
                        id,status,reason);
            }
            return;
        }
        supplierExamination.setStatus(statusEnum);
        if (reason != null) {
            supplierExamination.setReason(reason);
        }

        if (statusEnum == PASS) {
            handlePass(supplierExamination);
        }
        try {
            supplierExaminationRepository.saveAndFlush(supplierExamination);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败",e);
            }
            throw businessException("更新状态失败",e);
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
        List<SupplierExamination> toDelete = supplierExaminationRepository.findAll(
                ((Specification<SupplierExamination>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 检查是否可删除
        toDelete.forEach(e -> {
            if (e.getStatus() != FlowStatus.EDIT) {
                throw businessException("流程已启动，不能删除");
            }
        });
        try {
            supplierExaminationRepository.deleteAll(toDelete);
            supplierExaminationRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CgSupplierExaminationDetailsDto updateSupplierExaminationCredit(Integer id) {
        //查询供应商审核数据
        Optional<SupplierExamination> byId = supplierExaminationRepository.findById(id);
        SupplierExamination supplierExamination = byId.get();

        //信用信息有数据 先清空
        List<SupplierExaminationCredit> credits = supplierExamination.getCredits();
        if (credits.size() != 0) {
            supplierExaminationCreditRepository.deleteAll(credits);
        }
        SdebankEquityPenetration sd = new SdebankEquityPenetration();
        TokenResponse token = sd.getToken();
        EquityPenetrationDto equityPenetrationDto = new EquityPenetrationDto();
        equityPenetrationDto.setSupplierId(supplierExamination.getId());
        equityPenetrationDto.setName(supplierExamination.getName());
        equityPenetrationDto.setUnifiedSocialCreditCode(supplierExamination.getUnifiedSocialCreditCode());
        List<CreditDto> creditDtos = sd.raExCompanyFxMx(token, equityPenetrationDto);
        if (creditDtos.size() != 0) {
            List<SupplierExaminationCredit> list = new ArrayList<>();
            for (CreditDto creditDto : creditDtos) {
                SupplierExaminationCredit supplierExaminationCredit = getSupplierExaminationCredit(creditDto);
                supplierExaminationCredit.setSupplierExamination(byId.get());
                list.add(supplierExaminationCredit);

            }
            //更新数据
            try {
                supplierExamination.getCredits().clear();
                supplierExamination.getCredits().addAll(list);
                supplierExamination = supplierExaminationRepository.save(supplierExamination);
            } catch (Exception e) {
                log.error("供应商信用信息表更新失败!");
            }
        }
        return supplierExaminationDetailsMapper.toDto(supplierExamination);
    }


    /**
     * 通过处理
     * @param supplierExamination
     */
    @Transactional(rollbackFor = Exception.class)
    protected void handlePass(SupplierExamination supplierExamination) {
        if (log.isInfoEnabled()) {
            log.info("执行供应商审核通过处理，审核ID：{}",supplierExamination.getId());
        }
        supplierExamination.setPassTime(LocalDateTime.now());
        Supplier supplier;
        if (supplierExamination.getCategory() == SupplierExamination.Category.INITIAL) { // 新增
            supplier = supplierExaminationMapper.toSupplier(supplierExamination);
            SupplierAccount account = supplier.getAccount();
            account.setSupplier(supplier);
        } else { // 修改
            SupplierAccount account = supplierExamination.getAccount();
            Supplier currentSupplier = account.getSupplier();
            if (currentSupplier == null) {
                throw businessException("在供应商库中找不到该供应商，无法修改");
            }

            if (supplierExamination.getCategory() == SupplierExamination.Category.MODIFY_FROM_PORTAL) {
                // 门户修改的不能修改停用相关字段，将字段还原
                supplierExamination.setDisableTermStart(currentSupplier.getDisableTermStart());
                supplierExamination.setDisableTermEnd(currentSupplier.getDisableTermEnd());
            }

            try {
                supplier = supplierExaminationMapper.updateSupplier(currentSupplier,supplierExamination);
            }catch (Exception e){
                throw businessException("更新供应商失败");
            }
        }
        // 把主联系人的手机号更新到账号绑定的手机号
        SupplierAccount account = supplier.getAccount();
        supplier.getContacts().stream()
                .filter(SupplierContacts::getPrimary)
                .map(SupplierContacts::getMobile)
                .filter(e -> !e.equals(account.getMobile()))
                .findFirst()
                .ifPresent(account::setMobile);
    }

    /**
     * 保存前处理
     * @param supplierExamination
     */
    protected void preSave(SupplierExamination supplierExamination) {
        if (supplierExamination.getStatus() == FlowStatus.PASS) {
            throw businessException("流程已通过，不能修改");
        }
        List<SupplierExaminationContacts> contacts = supplierExamination.getContacts();
        if (CollectionUtils.isEmpty(contacts)) {
            throw businessException("联系人信息不能为空");
        }
        Object[] primaryContacts = contacts.stream().filter(SupplierExaminationContacts::getPrimary).toArray();
        if (primaryContacts.length != 1) {
            throw businessException("联系人信息必须有且仅有一个主联系人");
        }
        List<SupplierExaminationAttachment> attachments = supplierExamination.getAttachments();
        if (CollectionUtils.isEmpty(attachments)) {
            throw businessException("附件信息不能为空");
        }

        // 检查必要的附件是否都有
        loop:
        for (SupplierAttachmentCategory o : NECESSARY_ATTACHMENT_CATEGORY) {
            for (SupplierExaminationAttachment attachment : attachments) {
                if (attachment.getCategory() == o) {
                    continue loop;
                }
            }
            throw businessException("缺少附件[" + o.getZh() + "]");
        }

        if (SupplierExamination.Category.INITIAL == supplierExamination.getCategory()
                && supplierRepository.existsByTenantIdAndUnifiedSocialCreditCode(DataScopeUtils.loadTenantId(),supplierExamination.getUnifiedSocialCreditCode())) {
            throw businessException("统一社会信用代码已存在");
        }
    }

    protected SupplierExaminationCredit getSupplierExaminationCredit(CreditDto dto) {
        SupplierExaminationCredit supplierExaminationCredit = new SupplierExaminationCredit();
        supplierExaminationCredit.setDocumentNumbers(getString(dto.getDocumentNumbers()));
        supplierExaminationCredit.setPenaltyItemNames(getString(dto.getPenaltyItemNames()));
        supplierExaminationCredit.setPenaltyCauses(getString(dto.getPenaltyCauses()));
        supplierExaminationCredit.setPenaltyDates(getString(dto.getPenaltyDates()));
        supplierExaminationCredit.setPenaltyType(dto.getPenaltyType());
        return supplierExaminationCredit;
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
