package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.cg.core.domain.ProductLibrary;
import com.realfinance.sofa.cg.core.domain.plan.AnnualPlan;
import com.realfinance.sofa.cg.core.domain.plan.PurchasePlan;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.facade.CgPurchasePlanFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.AnnualPlanRepository;
import com.realfinance.sofa.cg.core.repository.PurchasePlanRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.*;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.core.domain.FlowStatus.EDIT;
import static com.realfinance.sofa.cg.core.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.core.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgPurchasePlanFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgPurchasePlanlmpl implements CgPurchasePlanFacade {
    private static final Logger log = LoggerFactory.getLogger(CgPurchasePlanlmpl.class);


    private final JpaQueryHelper jpaQueryHelper;
    private final PurchasePlanRepository purchasePlanRepository;
    private final PurchasePlanSaveMapper purchasePlanSaveMapper;
    private final PurchasePlanMapper purchasePlanMapper;
    private final AnnualPlanRepository annualPlanRepository;
    private final AnnualPlanMapper annualPlanMapper;
    private final AnnualPlanDetailsMapper annualPlanDetailsMapper;
    private final AnnualPlanSaveMapper annualPlanSaveMapper;

    public CgPurchasePlanlmpl(JpaQueryHelper jpaQueryHelper, PurchasePlanRepository purchasePlanRepository, PurchasePlanSaveMapper purchasePlanSaveMapper, PurchasePlanMapper purchasePlanMapper, AnnualPlanRepository annualPlanRepository, AnnualPlanMapper annualPlanMapper, AnnualPlanDetailsMapper annualPlanDetailsMapper, AnnualPlanSaveMapper annualPlanSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.purchasePlanRepository = purchasePlanRepository;
        this.purchasePlanSaveMapper = purchasePlanSaveMapper;
        this.purchasePlanMapper = purchasePlanMapper;
        this.annualPlanRepository = annualPlanRepository;
        this.annualPlanMapper = annualPlanMapper;
        this.annualPlanDetailsMapper = annualPlanDetailsMapper;
        this.annualPlanSaveMapper = annualPlanSaveMapper;
    }


    @Override
    public Page<AnnualPlanDto> list(Pageable pageable) {
        Page<AnnualPlan> all = annualPlanRepository.findAll(pageable);
        return all.map(annualPlanMapper::toDto);
    }

    @Override
    public List<AnnualPlanDto> list() {
        List<AnnualPlan> all = annualPlanRepository.findAll();
        return annualPlanMapper.toDtoList(all);
    }

    @Override
    public CgPurchasePlanDto getById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return purchasePlanMapper.toDto(getPurchasePlan(id));
    }

    @Override
    public Page<CgPurchasePlanDto> list(Pageable pageable,Integer id,CgPurchasePlanQueryCriteria queryCriteria) {
        Objects.requireNonNull(id);
        Page<PurchasePlan> page = purchasePlanRepository.findAll(new Specification<PurchasePlan>() {
            @Override
            public Predicate toPredicate(Root<PurchasePlan> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("annualPlan").get("id"), id));
                if (StringUtils.isNotBlank(queryCriteria.getProjectNameLike())){
                    predicates.add(criteriaBuilder.like(root.get("projectName"), "%" + queryCriteria.getProjectNameLike() + "%"));
                }
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        }, pageable);
        return page.map(purchasePlanMapper::toDto);

    }

    @Override
    public Page<CgPurchasePlanDto> list(CgPurchasePlanQueryCriteria queryCriteria, Pageable pageable) {
            Objects.requireNonNull(pageable);
            Page<PurchasePlan> result = purchasePlanRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                    .and(jpaQueryHelper.dataRuleSpecification()), pageable);
            return result.map(purchasePlanMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(AnnualPlanSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        AnnualPlan annualPlan;
        if (saveDto.getId() == null) { // 新增
            annualPlan = annualPlanSaveMapper.toEntity(saveDto);
            annualPlan.setTenantId(DataScopeUtils.loadTenantId());
            annualPlan.setStatus(EDIT);

        } else {//修改
            AnnualPlan entity = getAnnualPlan(saveDto.getId());
            annualPlan = annualPlanSaveMapper.updateEntity(entity, saveDto);
        }
        try {
            AnnualPlan saved = annualPlanRepository.saveAndFlush(annualPlan);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败,年度不能重复", e);
            }
            throw businessException("保存失败,年度不能重复");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgPurchasePlanSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        PurchasePlan purchasePlan;
        if (saveDto.getId() == null) {// 新增
            purchasePlan = purchasePlanSaveMapper.toEntity(saveDto);
            purchasePlan.setTenantId(DataScopeUtils.loadTenantId());
        } else {//修改
            PurchasePlan entity = getPurchasePlan(saveDto.getId());
            purchasePlan = purchasePlanSaveMapper.updateEntity(entity, saveDto);
        }
        try {
            PurchasePlan saved = purchasePlanRepository.saveAndFlush(purchasePlan);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败,同一年度下项目编号不能重复", e);
            }
            throw businessException("保存失败,同一年度下项目编号不能重复");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveList(List<CgPurchasePlanImportDto> list, Integer id) {
        PurchasePlan purchasePlan;
        for (CgPurchasePlanImportDto importDto:list){
            importDto.setAnnualPlan(id);
            if (importDto.getId() == null) {// 新增
                CgPurchasePlanSaveDto SaveDto = mapper(importDto);

                if ("是".equals(importDto.getIsContinue())&&importDto.getIsContinue()!=null)
                {
                    SaveDto.setContinue(true);
                }
                else {
                    SaveDto.setContinue(false);
                }
                purchasePlan = purchasePlanSaveMapper.toEntity(SaveDto);
                purchasePlan.setTenantId(DataScopeUtils.loadTenantId());
                try {
                    PurchasePlan saved = purchasePlanRepository.saveAndFlush(purchasePlan);
                    return saved.getId();
                } catch (Exception e) {
                         if (log.isErrorEnabled()) {
                        log.error("保存失败,同一年度下项目编号不能重复", e);
                    }
                    throw businessException("保存失败,同一年度下项目编号不能重复");
                }

            }

        }
       return  list.size();
    }

    @Override
    public List<CgPurchasePlanDto> getList(CgPurchasePlanQueryCriteria queryCriteria) {
        List<PurchasePlan> result = purchasePlanRepository.findAll(toSpecification(queryCriteria).and(jpaQueryHelper.dataRuleSpecification()));
        return purchasePlanMapper.toDtoList(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        for (Integer id : ids) {
            purchasePlanRepository.deleteById(id);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(@NotNull Integer id, @NotNull String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);
        AnnualPlan annualPlan = annualPlanRepository.findById(id).orElseThrow(() -> entityNotFound(AnnualPlan.class, "id", id));
        FlowStatus currentStatusEnum = annualPlan.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id, status);
            }
            return;
        }
        annualPlan.setStatus(statusEnum);
        try {
            annualPlanRepository.saveAndFlush(annualPlan);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败", e);
            }
            throw businessException("更新状态失败", e);
        }
    }

    @Override
    public AnnualPlanDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        AnnualPlan plan = getAnnualPlan(id);
        return annualPlanDetailsMapper.toDto(plan);

    }


    /**
     * 根据ID获取实体 :年度计划
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected AnnualPlan getAnnualPlan(Integer id) {
        Objects.requireNonNull(id);
        List<AnnualPlan> all = annualPlanRepository.findAll(
                ((Specification<AnnualPlan>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!annualPlanRepository.existsById(id)) {
                throw entityNotFound(AnnualPlan.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }


    /**
     * 根据ID获取实体 ：采购计划
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected PurchasePlan getPurchasePlan(Integer id) {
        Objects.requireNonNull(id);
        List<PurchasePlan> all = purchasePlanRepository.findAll(
                ((Specification<PurchasePlan>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!purchasePlanRepository.existsById(id)) {
                throw entityNotFound(PurchasePlan.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }


    protected CgPurchasePlanSaveDto mapper(CgPurchasePlanImportDto importDto){
        CgPurchasePlanSaveDto saveDto = new CgPurchasePlanSaveDto();

          saveDto.setNumber(importDto.getNumber());
          saveDto.setProjectName(importDto.getProjectName());
          saveDto.setContentDescription(importDto.getContentDescription());
          saveDto.setEstimatedAmount(importDto.getEstimatedAmount());
          saveDto.setProjectClassification(importDto.getProjectClassification());
          saveDto.setPlannedExpenditure(importDto.getPlannedExpenditure());
          saveDto.setPlannedProcurementPeriod(importDto.getPlannedProcurementPeriod());
          saveDto.setPurchaseCategory(importDto.getPurchaseCategory());
          saveDto.setContractMode(importDto.getContractMode());
          saveDto.setSupplierNumber(importDto.getSupplierNumber());
          saveDto.setPurchaseApplicationDate(importDto.getPurchaseApplicationDate());
          saveDto.setImplementationDate(importDto.getImplementationDate());
          saveDto.setCoOrdinationDepartment(importDto.getCoOrdinationDepartment());
          saveDto.setCoOrdinationDepartmentContacts(importDto.getCoOrdinationDepartmentContacts());
          saveDto.setDemandDepartment(importDto.getDemandDepartment());
          saveDto.setDemandDepartmentContacts(importDto.getDemandDepartmentContacts());
          saveDto.setProcurementMethod(importDto.getProcurementMethod());
          saveDto.setRemarks(importDto.getRemarks());
          saveDto.setDueDate(importDto.getDueDate());
          saveDto.setAnnualPlan(importDto.getAnnualPlan());

          return  saveDto;
    }

}
