package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.ExpertLabel;
import com.realfinance.sofa.cg.core.domain.ExpertLabelType;
import com.realfinance.sofa.cg.core.facade.CgExpertLabelTypeFacade;
import com.realfinance.sofa.cg.core.model.CgExpertLabelTypeDto;
import com.realfinance.sofa.cg.core.model.CgExpertLabelTypeSaveDto;
import com.realfinance.sofa.cg.core.repository.ExpertLabelTypeRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.ExpertLabelTypeMapper;
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

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.dataAccessForbidden;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgExpertLabelTypeFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgExpertLabelTypeImpl implements CgExpertLabelTypeFacade {

    private static final Logger log = LoggerFactory.getLogger(CgExpertLabelTypeImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final ExpertLabelTypeRepository expertLabelTypeRepository;
    private final ExpertLabelTypeMapper expertLabelTypeMapper;

    public CgExpertLabelTypeImpl(JpaQueryHelper jpaQueryHelper, ExpertLabelTypeRepository expertLabelTypeRepository, ExpertLabelTypeMapper expertLabelTypeMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.expertLabelTypeRepository = expertLabelTypeRepository;
        this.expertLabelTypeMapper = expertLabelTypeMapper;
    }

    @Override
    public Page<CgExpertLabelTypeDto> list(String filter, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<ExpertLabelType> result = expertLabelTypeRepository.findAll(((Specification<ExpertLabelType>) (root, query, criteriaBuilder) -> {
            if (StringUtils.isNotEmpty(filter)) {
                query.where(criteriaBuilder.like(root.get("name"), "%" + filter + "%"));
            }
            return query.getRestriction();
        }).and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(expertLabelTypeMapper::toDto);
    }

   @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgExpertLabelTypeSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        ExpertLabelType ExpertLabelType;
        if (saveDto.getId() == null) { // 新增
            ExpertLabelType = new ExpertLabelType();
            ExpertLabelType.setName(saveDto.getName());
            ExpertLabelType.setTenantId(DataScopeUtils.loadTenantId());
        } else { // 修改
            ExpertLabelType = expertLabelTypeRepository.findById(saveDto.getId())
                    .orElseThrow(() -> entityNotFound(ExpertLabelType.class,"id",saveDto.getId()));
            DataScopeUtils.checkTenantCanAccess(ExpertLabelType.getTenantId());
            ExpertLabelType.setName(saveDto.getName());
        }

        try {
            return expertLabelTypeRepository.saveAndFlush(ExpertLabelType).getId();
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
        List<ExpertLabelType> toDelete = expertLabelTypeRepository.findAll(
                ((Specification<ExpertLabelType>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        toDelete.forEach(e -> {
            Set<ExpertLabel> labels = e.getLabels();
            if (!labels.isEmpty()) {
                throw businessException("该类型下已有标签，不能删除");
            }
        });
        try {
            expertLabelTypeRepository.deleteAll(toDelete);
            expertLabelTypeRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败",e);
            }
            throw businessException("删除失败");
        }
    }
}
