package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.cg.core.domain.PurchaseResultNotice;
import com.realfinance.sofa.cg.core.domain.PurchaseResultNoticeAttachment;
import com.realfinance.sofa.cg.core.domain.PurchaseResultNoticeSup;
import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.facade.CgPurchaseResultNoticeFacade;
import com.realfinance.sofa.cg.core.model.CgAttSaveDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeSaveDto;
import com.realfinance.sofa.cg.core.repository.ContractManageRepository;
import com.realfinance.sofa.cg.core.repository.ProjectRepository;
import com.realfinance.sofa.cg.core.repository.PurchaseResultNoticeRepository;
import com.realfinance.sofa.cg.core.repository.PurchaseResultRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.PurchaseResultMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.PurchaseResultNoticeMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.PurchaseResultNoticeSaveMapper;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;


@Service
@SofaService(interfaceType = CgPurchaseResultNoticeFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgPurchaseResultNoticelmpl implements CgPurchaseResultNoticeFacade {

    private static final Logger log = LoggerFactory.getLogger(CgPurchaseResultNoticelmpl.class);


    private final PurchaseResultNoticeRepository purchaseResultNoticeRepository;
    private final PurchaseResultRepository purchaseResultRepository;
    private final PurchaseResultMapper purchaseResultMapper;
    private final PurchaseResultNoticeSaveMapper purchaseResultNoticeSaveMapper;
    private final PurchaseResultNoticeMapper purchaseResultNoticeMapper;
    private final JpaQueryHelper jpaQueryHelper;
    private final ContractManageRepository contractManageRepository;
    private final ProjectRepository projectRepository;

    public CgPurchaseResultNoticelmpl(PurchaseResultNoticeRepository purchaseResultNoticeRepository, PurchaseResultRepository purchaseResultRepository, PurchaseResultMapper purchaseResultMapper, PurchaseResultNoticeSaveMapper purchaseResultNoticeSaveMapper, PurchaseResultNoticeMapper purchaseResultNoticeMapper, JpaQueryHelper jpaQueryHelper, ContractManageRepository contractManageRepository, ProjectRepository projectRepository) {
        this.purchaseResultNoticeRepository = purchaseResultNoticeRepository;
        this.purchaseResultRepository = purchaseResultRepository;
        this.purchaseResultMapper = purchaseResultMapper;
        this.purchaseResultNoticeSaveMapper = purchaseResultNoticeSaveMapper;
        this.purchaseResultNoticeMapper = purchaseResultNoticeMapper;
        this.jpaQueryHelper = jpaQueryHelper;
        this.contractManageRepository = contractManageRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Page<CgPurchaseResultNoticeDto> list(Pageable pageable, CgPurchaseResultNoticeQueryCriteria queryCriteria) {
        return purchaseResultNoticeRepository.findAll(new Specification<PurchaseResultNotice>() {
            @Override
            public Predicate toPredicate(Root<PurchaseResultNotice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (queryCriteria == null) {
                    return null;
                }
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(queryCriteria.getInsideTitleLike())) {
                    predicates.add(criteriaBuilder.like(root.get("insideTitle"), "%" + queryCriteria.getInsideTitleLike() + "%"));
                }
                if (StringUtils.isNotBlank(queryCriteria.getOutsideTitleLike())) {
                    predicates.add(criteriaBuilder.like(root.get("outsideTitle"), "%" + queryCriteria.getOutsideTitleLike() + "%"));
                }
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        }, pageable).map(purchaseResultNoticeMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgPurchaseResultNoticeSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        PurchaseResultNotice purchaseResultNotice;
        if (saveDto.getId() == null) {//新增
            purchaseResultNotice = purchaseResultNoticeSaveMapper.toEntity(saveDto);
            purchaseResultNotice.setTenantId(DataScopeUtils.loadTenantId());
            purchaseResultNotice.setStatus(FlowStatus.EDIT);
        } else {//修改
            PurchaseResultNotice entity = getPurchaseResultNotice(saveDto.getId());
            purchaseResultNotice = purchaseResultNoticeSaveMapper.updateEntity(entity, saveDto);
            if (entity.getStatus() == PASS) {
                throw businessException("流程已通过，不能修改");
            }
        }
        List<PurchaseResultNoticeSup> noticeSups = purchaseResultNotice.getResultNoticeSups();
        for (PurchaseResultNoticeSup sup : noticeSups) {
            if (sup.getId() != null) {
                sup.setId(null);
            }
        }
        purchaseResultNotice.setResultNoticeSups(noticeSups);
        try {
            PurchaseResultNotice saved = purchaseResultNoticeRepository.saveAndFlush(purchaseResultNotice);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }


    }

    @Override
    public CgPurchaseResultNoticeDto getDetailsById(@NotNull Integer id) {
        PurchaseResultNotice purchaseResultNotice = getPurchaseResultNotice(id);
        return purchaseResultNoticeMapper.toDto(purchaseResultNotice);
    }


    protected PurchaseResultNotice getPurchaseResultNotice(Integer id) {
        Objects.requireNonNull(id);
        List<PurchaseResultNotice> all = purchaseResultNoticeRepository.findAll(
                ((Specification<PurchaseResultNotice>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!purchaseResultNoticeRepository.existsById(id)) {
                throw entityNotFound(PurchaseResultNotice.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }


    /**
     * 更新处理状态
     *
     * @param id
     * @param status
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(@NotNull Integer id, @NotNull String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);
        PurchaseResultNotice purchaseResultNotice = purchaseResultNoticeRepository.findById(id).orElseThrow(() -> entityNotFound(PurchaseResultNotice.class, "id", id));
        FlowStatus currentStatusEnum = purchaseResultNotice.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id, status);
            }
            return;
        }
        purchaseResultNotice.setStatus(statusEnum);
        if (statusEnum == PASS) {
            //生成完成审批流的时间数据
            purchaseResultNotice.setPassTime(LocalDateTime.now());
            handlePass(purchaseResultNotice);
        }
        try {
            purchaseResultNoticeRepository.saveAndFlush(purchaseResultNotice);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败", e);
            }
            throw businessException("更新状态失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CgPurchaseResultNoticeDto updateAttachment(Integer id, List<CgAttSaveDto> dtoList) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(dtoList);
        PurchaseResultNotice purchaseResultNotice = purchaseResultNoticeRepository.findById(id).orElseThrow(() -> entityNotFound(PurchaseResultNotice.class, "id", id));
        Map<Integer, CgAttSaveDto> map = dtoList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        for (PurchaseResultNoticeAttachment attachment : purchaseResultNotice.getAttachments()) {
            if (attachment.getExt().equals("pdf")){
                CgAttSaveDto cgAttSaveDto = map.get(attachment.getId());
                attachment.setPath(cgAttSaveDto.getPath());
                attachment.setUploadTime(cgAttSaveDto.getUploadTime());
            }
        }
        PurchaseResultNotice p;
        try {
            p = purchaseResultNoticeRepository.saveAndFlush(purchaseResultNotice);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新附件失败", e);
            }
            throw businessException("更新附件失败", e);
        }
        return purchaseResultNoticeMapper.toDto(p);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void text(String note) {
        PurchaseResultNotice purchaseResultNotice = purchaseResultNoticeRepository.findById(1000055).get();
        purchaseResultNotice.setInsideContent(note);
        purchaseResultNotice.setOutsideContent(note);
        purchaseResultNoticeRepository.save(purchaseResultNotice);
    }

    @Override
    public List<CgPurchaseResultNoticeDto> listByProjectId(List<Integer> projectIds) {
        List<PurchaseResultNotice> list = purchaseResultNoticeRepository.findByProjectIdIn(projectIds);
        return purchaseResultNoticeMapper.toDtoList(list);
    }

    @Override
    public Integer getIdByProjectId(Integer projectId) {
        Objects.requireNonNull(projectId);
        return purchaseResultNoticeRepository.findByProjectId(projectId).map(PurchaseResultNotice::getId).orElse(null);
    }

    /**
     * 通过处理
     *
     * @param purchaseResultNotice
     */
    protected void handlePass(PurchaseResultNotice purchaseResultNotice) {
        if (log.isInfoEnabled()) {
            log.info("采购结果通知审核通过处理，审核ID：{}", purchaseResultNotice.getId());
        }
        ContractManage contractManage = new ContractManage();
        contractManage.setTenantId(purchaseResultNotice.getTenantId());
        contractManage.setCreatedTime(LocalDateTime.now());
        contractManage.setCreatedUserId(purchaseResultNotice.getCreatedUserId());
        contractManage.setModifiedTime(LocalDateTime.now());
        contractManage.setModifiedUserId(purchaseResultNotice.getModifiedUserId());
        contractManage.setProject(getProject(purchaseResultNotice.getProjectId()));
        contractManage.setFileStatus(0);
        try {
            ContractManage saved = contractManageRepository.saveAndFlush(contractManage);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败,becauser:" + e.getMessage());
        }
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected Project getProject(Integer id) {
        Objects.requireNonNull(id);
        List<Project> all = projectRepository.findAll(
                ((Specification<Project>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!projectRepository.existsById(id)) {
                throw entityNotFound(Project.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }
}
