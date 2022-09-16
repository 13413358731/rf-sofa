package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationMain;
import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationProcessMng;
import com.realfinance.sofa.cg.sup.domain.SupplierEvaluationSub;
import com.realfinance.sofa.cg.sup.facade.CgSupEvaluationProcessMngFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierEvaluationFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.sup.repository.SupEvaluationProcessMngRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierAssessmentIndicatorRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierEvaluationRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.*;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.dataAccessForbidden;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgSupEvaluationProcessMngFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupEvaluationProcessMngImpl implements CgSupEvaluationProcessMngFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupEvaluationProcessMngImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final SupEvaluationProcessMngRepository supEvaluationProcessMngRepository;
    private final SupplierAssessmentIndicatorRepository supplierAssessmentIndicatorRepository;
    private final SupplierEvaluationRepository supplierEvaluationRepository;
    private final SupEvaluationProcessMngMapper supEvaluationProcessMngMapper;
    private final SupplierAssessmentIndicatorMapper supplierAssessmentIndicatorMapper;
    private final SupEvaluationProcessMngDetailsMapper supEvaluationProcessMngDetailsMapper;
    private final SupEvaluationProcessMngSaveMapper supEvaluationProcessMngSaveMapper;


    public CgSupEvaluationProcessMngImpl(JpaQueryHelper jpaQueryHelper, SupEvaluationProcessMngRepository supEvaluationProcessMngRepository, SupplierAssessmentIndicatorRepository supplierAssessmentIndicatorRepository, SupplierEvaluationRepository supplierEvaluationRepository, SupEvaluationProcessMngMapper supEvaluationProcessMngMapper, SupplierAssessmentIndicatorMapper supplierAssessmentIndicatorMapper, SupEvaluationProcessMngDetailsMapper supEvaluationProcessMngDetailsMapper, SupEvaluationProcessMngSaveMapper supEvaluationProcessMngSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.supEvaluationProcessMngRepository = supEvaluationProcessMngRepository;
        this.supplierAssessmentIndicatorRepository = supplierAssessmentIndicatorRepository;
        this.supplierEvaluationRepository = supplierEvaluationRepository;
        this.supEvaluationProcessMngMapper = supEvaluationProcessMngMapper;
        this.supplierAssessmentIndicatorMapper = supplierAssessmentIndicatorMapper;
        this.supEvaluationProcessMngDetailsMapper = supEvaluationProcessMngDetailsMapper;
        this.supEvaluationProcessMngSaveMapper = supEvaluationProcessMngSaveMapper;
    }

    @Override
    public Page<CgSupEvaluationProcessMngDto> list(CgSupEvaluationProcessMngQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierEvaluationProcessMng> result = supEvaluationProcessMngRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supEvaluationProcessMngMapper::toDto);
    }

    @Override
    public CgSupEvaluationProcessMngDetailsDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return supEvaluationProcessMngDetailsMapper.toDto(getSupEvaluationProcessMng(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(@NotNull CgSupEvaluationProcessMngDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        SupplierEvaluationProcessMng supplierEvaluationProcessMng;
        System.out.println(saveDto.getId());
        if (saveDto.getId() == null) { // 新增
            supplierEvaluationProcessMng = supEvaluationProcessMngSaveMapper.toEntity(saveDto);
            supplierEvaluationProcessMng.setTenantId(DataScopeUtils.loadTenantId());
            supplierEvaluationProcessMng.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
            supplierEvaluationProcessMng.setEvaluationStartDate(LocalDate.now());
            supplierEvaluationProcessMng.setEvaluationStatus(0);
            supplierEvaluationProcessMng.setReleaseStatus(0);
        } else { // 修改
            SupplierEvaluationProcessMng entity = getSupEvaluationProcessMng(saveDto.getId());
            supplierEvaluationProcessMng = supEvaluationProcessMngSaveMapper.updateEntity(entity,saveDto);
        }
        try {
            SupplierEvaluationProcessMng saved = supEvaluationProcessMngRepository.saveAndFlush(supplierEvaluationProcessMng);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer startevaluate(@NotNull List<CgSupplierEvaluationSheetSubDto> cgSupplierEvaluationSubDtos,@NotNull Integer id) {
        Objects.requireNonNull(id);
        SupplierEvaluationProcessMng supplierEvaluationProcessMng = getSupEvaluationProcessMng(id);
        CgSupEvaluationProcessMngDetailsDto supEvaluationProcessMng = supEvaluationProcessMngDetailsMapper.toDto(supplierEvaluationProcessMng);
        for (CgSupEvaluationDepartmentDto supplierEvaluationDepartment : supEvaluationProcessMng.getSupplierEvaluationDepartments()) {
            for (CgSupEvaluatorDto supplierEvaluator : supplierEvaluationDepartment.getSupplierEvaluators()) {
                for (CgSupplierEvaluationSheetSubDto cgSupplierEvaluationSubDto : cgSupplierEvaluationSubDtos) {
                    SupplierEvaluationMain supplierEvaluationMain = new SupplierEvaluationMain();
                    List<SupplierEvaluationSub> supplierEvaluationSubs= new ArrayList<SupplierEvaluationSub>();
                    supplierEvaluationMain.setTenantId(DataScopeUtils.loadTenantId());
                    supplierEvaluationMain.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
                    supplierEvaluationMain.setEvaluationStartNo(supEvaluationProcessMng.getEvaluationStartNo());
                    supplierEvaluationMain.setEvaluationStartName(supEvaluationProcessMng.getEvaluationStartName());
                    supplierEvaluationMain.setEvaluationSheetNo(supEvaluationProcessMng.getEvaluationSheetNo());
                    supplierEvaluationMain.setEvaluationStartDate(supEvaluationProcessMng.getEvaluationStartDate());
                    supplierEvaluationMain.setEvaluationEndDate(supEvaluationProcessMng.getEvaluationEndDate());
                    supplierEvaluationMain.setStatus(supEvaluationProcessMng.getEvaluationStatus());
                    supplierEvaluationMain.setFinishDate(supEvaluationProcessMng.getEvaluationEndDate());
                    supplierEvaluationMain.setReleaseDate(LocalDate.now());
                    supplierEvaluationMain.setSupplierEvaluationDepartment(supplierEvaluationDepartment.getId());
                    supplierEvaluationMain.setEvaluator(supplierEvaluator.getId());

                    for (CgSupplierEvaluationSheetGrandsonDto supplierEvaluationSheetGrandson : cgSupplierEvaluationSubDto.getSupplierEvaluationSheetGrandsons()) {
                        SupplierEvaluationSub supplierEvaluationSub = new SupplierEvaluationSub();
                        supplierEvaluationSub.setAssessmentNo(cgSupplierEvaluationSubDto.getAssessmentNo());
                        supplierEvaluationSub.setAssessmentName(cgSupplierEvaluationSubDto.getAssessmentName());
                        supplierEvaluationSub.setAssessmentWeight(cgSupplierEvaluationSubDto.getAssessmentWeight());
                        supplierEvaluationSub.setAssessmentIndicatorNo(supplierEvaluationSheetGrandson.getAssessmentIndicatorNo());
                        supplierEvaluationSub.setAssessmentIndicatorName(supplierEvaluationSheetGrandson.getAssessmentIndicatorName());
                        supplierEvaluationSub.setCalculation(supplierEvaluationSheetGrandson.getCalculation());
                        supplierEvaluationSub.setIndicatorWeight(supplierEvaluationSheetGrandson.getIndicatorWeight());
                        supplierEvaluationSubs.add(supplierEvaluationSub);
                    }
                    supplierEvaluationMain.setSupplierEvaluationSubs(supplierEvaluationSubs);

                    try {
                        supplierEvaluationRepository.saveAndFlush(supplierEvaluationMain);
//                    return saved.getId();
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("保存失败",e);
                        }
                        throw businessException("保存失败,because:"+e.getMessage());
                    }
                }
            }
        }

        supplierEvaluationProcessMng.setReleaseStatus(1);
        try {
            SupplierEvaluationProcessMng saved = supEvaluationProcessMngRepository.saveAndFlush(supplierEvaluationProcessMng);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }

//        return ;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<SupplierEvaluationProcessMng> toDelete = supEvaluationProcessMngRepository.findAll(
                ((Specification<SupplierEvaluationProcessMng>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 删除
        try {
            supEvaluationProcessMngRepository.deleteAll(toDelete);
            supEvaluationProcessMngRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected SupplierEvaluationProcessMng getSupEvaluationProcessMng(Integer id) {
        Objects.requireNonNull(id);
        List<SupplierEvaluationProcessMng> all = supEvaluationProcessMngRepository.findAll(
                ((Specification<SupplierEvaluationProcessMng>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!supEvaluationProcessMngRepository.existsById(id)) {
                System.out.println("找不到相关的评估流程管理");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

}
