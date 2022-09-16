package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.proj.ProjectSchedule;
import com.realfinance.sofa.cg.core.domain.proj.ProjectScheduleUser;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.facade.CgProjectScheduleFacade;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleQueryCriteria;
import com.realfinance.sofa.cg.core.model.CgRequirementDetailsDto;
import com.realfinance.sofa.cg.core.model.CgRequirementDto;
import com.realfinance.sofa.cg.core.repository.ProjectScheduleRepository;
import com.realfinance.sofa.cg.core.repository.RequirementRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectScheduleDetailsSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectScheduleMapper;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.checkerframework.checker.units.qual.C;
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

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.dataAccessForbidden;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.entityNotFound;

/**
 * @author hhq
 * @date 2021/6/21 - 18:23
 */
@Service
@SofaService(interfaceType = CgProjectScheduleFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgProjectScheduleImpl implements CgProjectScheduleFacade {

    private static final Logger log = LoggerFactory.getLogger(CgProjectScheduleImpl.class);
    private final JpaQueryHelper jpaQueryHelper;
    private final ProjectScheduleRepository projectScheduleRepository;
    private final ProjectScheduleMapper projectScheduleMapper;
    private final ProjectScheduleDetailsSaveMapper projectScheduleDetailsSaveMapper;
    private final RequirementRepository requirementRepository;

    public CgProjectScheduleImpl(JpaQueryHelper jpaQueryHelper,
                                 ProjectScheduleRepository projectScheduleRepository,
                                 ProjectScheduleMapper projectScheduleMapper,
                                 ProjectScheduleDetailsSaveMapper projectScheduleDetailsSaveMapper,
                                 RequirementRepository requirementRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.projectScheduleRepository=projectScheduleRepository;
        this.projectScheduleMapper = projectScheduleMapper;
        this.projectScheduleDetailsSaveMapper=projectScheduleDetailsSaveMapper;
        this.requirementRepository=requirementRepository;
    }

    @Override
    public Page<CgProjectScheduleDto> list(CgProjectScheduleQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<ProjectSchedule> result = projectScheduleRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(projectScheduleMapper::toDto);
    }

    protected Requirement getRequirement(Integer id) {
        Objects.requireNonNull(id);
        List<Requirement> all = requirementRepository.findAll(
                ((Specification<Requirement>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!requirementRepository.existsById(id)) {
                throw entityNotFound(Requirement.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgProjectScheduleDto saveDto) {
        ProjectSchedule projectSchedule = projectScheduleDetailsSaveMapper.toEntity(saveDto);
        try{
            ProjectSchedule projectSchedule1 = projectScheduleRepository.saveAndFlush(projectSchedule);
            return projectSchedule1.getId();
        }catch (Exception e){
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }*/

    protected ProjectSchedule getProjectSchedule(Integer id){
        Objects.requireNonNull(id);
        List<ProjectSchedule> all = projectScheduleRepository.findAll(
                ((Specification<ProjectSchedule>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!projectScheduleRepository.existsById(id)) {
                System.out.println("找不到相应项目进度");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

}
