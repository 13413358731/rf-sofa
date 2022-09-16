package com.realfinance.sofa.cg.sup.impl;


import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.SupplierAnnouncement;
import com.realfinance.sofa.cg.sup.domain.SupplierBadBehavior;
import com.realfinance.sofa.cg.sup.facade.CgSupplierBadBehaviorFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierSolicitationFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierBadBehaviorDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierBadBehaviorQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgSupplierBadBehaviorSaveDto;
import com.realfinance.sofa.cg.sup.repository.SupplierBadBehaviorRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierBadBehaviorMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierBadBehaviorSaveMapper;
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

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgSupplierBadBehaviorFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class SupplierBadBehaviorImpl implements CgSupplierBadBehaviorFacade {

    private static final Logger log = LoggerFactory.getLogger(SupplierBadBehaviorImpl.class);
    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierBadBehaviorRepository supplierBadBehaviorRepository;
    private final SupplierBadBehaviorMapper supplierBadBehaviorMapper;
    private final SupplierBadBehaviorSaveMapper supplierBadBehaviorSaveMapper;

    public SupplierBadBehaviorImpl(SupplierBadBehaviorRepository supplierBadBehaviorRepository,
                                   SupplierBadBehaviorMapper supplierBadBehaviorMapper,
                                   SupplierBadBehaviorSaveMapper supplierBadBehaviorSaveMapper,
                                   JpaQueryHelper jpaQueryHelper
    ) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.supplierBadBehaviorMapper = supplierBadBehaviorMapper;
        this.supplierBadBehaviorSaveMapper = supplierBadBehaviorSaveMapper;
        this.supplierBadBehaviorRepository = supplierBadBehaviorRepository;
    }


    /**
     * 保存更新
     *
     * @param saveDto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgSupplierBadBehaviorSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        SupplierBadBehavior supplierBadBehavior;
        if (saveDto.getId() == null) {//新增
            supplierBadBehavior = supplierBadBehaviorSaveMapper.toEntity(saveDto);
            supplierBadBehavior.setTenantId(DataScopeUtils.loadTenantId());
            supplierBadBehavior.setDepartmentId(DataScopeUtils.loadDepartmentId().get());
        } else {
            SupplierBadBehavior entity = getSupplierBadBehavior(saveDto.getId());
            supplierBadBehavior = supplierBadBehaviorSaveMapper.updateEntity(entity, saveDto);
        }
        try {
            SupplierBadBehavior saved = supplierBadBehaviorRepository.saveAndFlush(supplierBadBehavior);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }

    }


    /**
     * 删除
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NotNull Set<Integer> ids) {
        Objects.requireNonNull(ids);
        for (Integer id : ids) {
            supplierBadBehaviorRepository.deleteById(id);
        }
    }

    /**
     * 列表
     *
     * @param
     */
    @Override
    public Page<CgSupplierBadBehaviorDto> list(Pageable pageable, CgSupplierBadBehaviorQueryCriteria queryCriteria) {
        return supplierBadBehaviorRepository.findAll(new Specification<SupplierBadBehavior>() {
            @Override
            public Predicate toPredicate(Root<SupplierBadBehavior> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (queryCriteria==null)
                {return  null;
                }
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(queryCriteria.getSupplierNameLike())){
                    predicates.add(criteriaBuilder.like(root.get("supplierName"),"%"+queryCriteria.getSupplierNameLike()+"%"));
                }
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        },pageable).map(supplierBadBehaviorMapper::toDto);

    }


    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected SupplierBadBehavior getSupplierBadBehavior(Integer id) {
        Objects.requireNonNull(id);
        List<SupplierBadBehavior> all = supplierBadBehaviorRepository.findAll(
                ((Specification<SupplierBadBehavior>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!supplierBadBehaviorRepository.existsById(id)) {
                throw entityNotFound(SupplierBadBehavior.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

}
