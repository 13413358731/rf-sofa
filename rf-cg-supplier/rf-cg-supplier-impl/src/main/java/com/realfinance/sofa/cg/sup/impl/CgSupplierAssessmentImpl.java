package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.SupplierAssessment;
import com.realfinance.sofa.cg.sup.domain.SupplierAssessmentIndicator;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAssessmentFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.sup.repository.SupplierAssessmentIndicatorRepository;
import com.realfinance.sofa.cg.sup.repository.SupplierAssessmentRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAssessmentDetailsMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAssessmentIndicatorMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAssessmentMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAssessmentSaveMapper;
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
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.dataAccessForbidden;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgSupplierAssessmentFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierAssessmentImpl implements CgSupplierAssessmentFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupplierAssessmentImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierAssessmentRepository supplierAssessmentRepository;
    private final SupplierAssessmentIndicatorRepository supplierAssessmentIndicatorRepository;
    private final SupplierAssessmentMapper supplierAssessmentMapper;
    private final SupplierAssessmentIndicatorMapper supplierAssessmentIndicatorMapper;
    private final SupplierAssessmentDetailsMapper supplierAssessmentDetailsMapper;
    private final SupplierAssessmentSaveMapper supplierAssessmentSaveMapper;

    public CgSupplierAssessmentImpl(JpaQueryHelper jpaQueryHelper, SupplierAssessmentRepository supplierAssessmentRepository, SupplierAssessmentIndicatorRepository supplierAssessmentIndicatorRepository, SupplierAssessmentMapper supplierAssessmentMapper, SupplierAssessmentIndicatorMapper supplierAssessmentIndicatorMapper, SupplierAssessmentDetailsMapper supplierAssessmentDetailsMapper, SupplierAssessmentSaveMapper supplierAssessmentSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.supplierAssessmentRepository = supplierAssessmentRepository;
        this.supplierAssessmentIndicatorRepository = supplierAssessmentIndicatorRepository;
        this.supplierAssessmentMapper = supplierAssessmentMapper;
        this.supplierAssessmentIndicatorMapper = supplierAssessmentIndicatorMapper;
        this.supplierAssessmentDetailsMapper = supplierAssessmentDetailsMapper;
        this.supplierAssessmentSaveMapper = supplierAssessmentSaveMapper;
    }

    @Override
    public Page<CgSupplierAssessmentDto> list(CgSupplierAssessmentQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierAssessment> result = supplierAssessmentRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierAssessmentMapper::toDto);
    }

    @Override
    public CgSupplierAssessmentDetailsDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return supplierAssessmentDetailsMapper.toDto(getSupplierAssessment(id));
    }

    @Override
    public Page<CgSupplierAssessmentDto> queryRefer(CgSupplierAssessmentQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierAssessment> result = supplierAssessmentRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierAssessmentMapper::toDto);
    }

    @Override
    public Page<CgSupplierAssessmentIndicatorDto> queryIndicatorRefer(CgSupplierAssessmentIndicatorQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierAssessmentIndicator> result = supplierAssessmentIndicatorRepository.findAll(toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierAssessmentIndicatorMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(@NotNull CgSupplierAssessmentDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        SupplierAssessment supplierAssessment;
        System.out.println(saveDto.getId());
        if (saveDto.getId() == null) { // 新增
            supplierAssessment = supplierAssessmentSaveMapper.toEntity(saveDto);
            supplierAssessment.setTenantId(DataScopeUtils.loadTenantId());
            supplierAssessment.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
        } else { // 修改
            SupplierAssessment entity = getSupplierAssessment(saveDto.getId());
            supplierAssessment = supplierAssessmentSaveMapper.updateEntity(entity,saveDto);
        }
        try {
            SupplierAssessment saved = supplierAssessmentRepository.saveAndFlush(supplierAssessment);
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
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<SupplierAssessment> toDelete = supplierAssessmentRepository.findAll(
                ((Specification<SupplierAssessment>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 删除
        try {
            supplierAssessmentRepository.deleteAll(toDelete);
            supplierAssessmentRepository.flush();
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
    protected SupplierAssessment getSupplierAssessment(Integer id) {
        Objects.requireNonNull(id);
        List<SupplierAssessment> all = supplierAssessmentRepository.findAll(
                ((Specification<SupplierAssessment>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!supplierAssessmentRepository.existsById(id)) {
                System.out.println("找不到相关的考核");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

}
