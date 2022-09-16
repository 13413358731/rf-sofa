package com.realfinance.sofa.cg.core.impl;


import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.Parameter;
import com.realfinance.sofa.cg.core.facade.CgParameterFacade;
import com.realfinance.sofa.cg.core.model.CgParameterQueryCriteria;
import com.realfinance.sofa.cg.core.model.ParameterDto;
import com.realfinance.sofa.cg.core.model.ParameterSaveDto;
import com.realfinance.sofa.cg.core.repository.ParameterRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.ParameterMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ParameterSaveMapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgParameterFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgParameterImpl implements CgParameterFacade {

    private static final Logger log = LoggerFactory.getLogger(CgParameterImpl.class);

    private final ParameterRepository parameterRepository;
    private final ParameterMapper parameterMapper;
    private final ParameterSaveMapper parameterSaveMapper;
    private final JpaQueryHelper jpaQueryHelper;

    public  CgParameterImpl(ParameterRepository parameterRepository,
                            ParameterMapper parameterMapper,
                            ParameterSaveMapper parameterSaveMapper,
                            JpaQueryHelper jpaQueryHelper
                            ){
        this.jpaQueryHelper=jpaQueryHelper;
        this.parameterMapper=parameterMapper;
        this.parameterSaveMapper=parameterSaveMapper;
        this.parameterRepository=parameterRepository;


    }

    /**
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<ParameterDto> list(Pageable pageable, CgParameterQueryCriteria queryCriteria) {
        Page<Parameter> page = parameterRepository.findAll(new Specification<Parameter>() {
            @Override
            public Predicate toPredicate(Root<Parameter> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (queryCriteria == null) {
                    return null;
                }
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(queryCriteria.getCodeLike())){
                    predicates.add(criteriaBuilder.like(root.get("parameterCode"),"%"+queryCriteria.getCodeLike()+"%"));
                }
                if (StringUtils.isNotBlank(queryCriteria.getNameLike())){
                    predicates.add(criteriaBuilder.like(root.get("parameterName"),"%"+queryCriteria.getNameLike()+"%"));
                }
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();

            }
        },pageable);
       return page.map(parameterMapper::toDto);
    }

    /**
     *
     * @param saveDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(ParameterSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        Parameter entity = getParameter(saveDto.getId());
        Parameter parameter= parameterSaveMapper.updateEntity(entity, saveDto);
        parameter.setTenantId(DataScopeUtils.loadTenantId());
        try {
            Parameter saved = parameterRepository.saveAndFlush(parameter);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }
    }


    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected Parameter getParameter(Integer id) {
        Objects.requireNonNull(id);
        List<Parameter> all = parameterRepository.findAll(
                ((Specification<Parameter>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!parameterRepository.existsById(id)) {
                throw entityNotFound(Parameter.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }


    @Override
    public ParameterDto findByParameterCode(String parameterCode) {
        Parameter parameter = parameterRepository.findByParameterCode(parameterCode);
        return parameterMapper.toDto(parameter);
    }
}
