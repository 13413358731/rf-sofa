package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.SupplierLabel;
import com.realfinance.sofa.cg.sup.domain.SupplierLabelType;
import com.realfinance.sofa.cg.sup.facade.CgSupplierLabelTypeFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelTypeDto;
import com.realfinance.sofa.cg.sup.model.CgSupplierLabelTypeSaveDto;
import com.realfinance.sofa.cg.sup.repository.SupplierLabelTypeRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierLabelTypeMapper;
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

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgSupplierLabelTypeFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgSupplierLabelTypeImpl implements CgSupplierLabelTypeFacade {

    private static final Logger log = LoggerFactory.getLogger(CgSupplierLabelTypeImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final SupplierLabelTypeRepository supplierLabelTypeRepository;
    private final SupplierLabelTypeMapper supplierLabelTypeMapper;

    public CgSupplierLabelTypeImpl(JpaQueryHelper jpaQueryHelper,
                                   SupplierLabelTypeRepository supplierLabelTypeRepository,
                                   SupplierLabelTypeMapper supplierLabelTypeMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.supplierLabelTypeRepository = supplierLabelTypeRepository;
        this.supplierLabelTypeMapper = supplierLabelTypeMapper;
    }

    @Override
    public Page<CgSupplierLabelTypeDto> list(String filter, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<SupplierLabelType> result = supplierLabelTypeRepository.findAll(((Specification<SupplierLabelType>) (root, query, criteriaBuilder) -> {
            if (StringUtils.isNotEmpty(filter)) {
                query.where(criteriaBuilder.like(root.get("name"), "%" + filter + "%"));
            }
            return query.getRestriction();
        }).and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(supplierLabelTypeMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgSupplierLabelTypeSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        SupplierLabelType supplierLabelType;
        if (saveDto.getId() == null) { // 新增
            supplierLabelType = new SupplierLabelType();
            supplierLabelType.setName(saveDto.getName());
            supplierLabelType.setTenantId(DataScopeUtils.loadTenantId());
        } else { // 修改
            supplierLabelType = supplierLabelTypeRepository.findById(saveDto.getId())
                    .orElseThrow(() -> entityNotFound(SupplierLabelType.class,"id",saveDto.getId()));
            DataScopeUtils.checkTenantCanAccess(supplierLabelType.getTenantId());
            supplierLabelType.setName(saveDto.getName());
        }

        try {
            return supplierLabelTypeRepository.saveAndFlush(supplierLabelType).getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败");
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
        List<SupplierLabelType> toDelete = supplierLabelTypeRepository.findAll(
                ((Specification<SupplierLabelType>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        toDelete.forEach(e -> {
            Set<SupplierLabel> labels = e.getLabels();
            if (!labels.isEmpty()) {
                throw businessException("该类型下已有标签，不能删除");
            }
        });
        try {
            supplierLabelTypeRepository.deleteAll(toDelete);
            supplierLabelTypeRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }
}
