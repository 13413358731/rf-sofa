package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.proj.ProjectSchedule;
import com.realfinance.sofa.cg.core.domain.proj.ProjectScheduleUser;
import com.realfinance.sofa.cg.core.facade.CgProjectScheduleUserFacade;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleUserDto;
import com.realfinance.sofa.cg.core.repository.ProjectScheduleRepository;
import com.realfinance.sofa.cg.core.repository.ProjectScheduleUserRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectScheduleDetailsSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectScheduleUserDetailsSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectScheduleUserMapper;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@SofaService(interfaceType = CgProjectScheduleUserFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
//@Transactional(readOnly = true)
public class CgProjectScheduleUserImpl implements CgProjectScheduleUserFacade {

    private static final Logger log = LoggerFactory.getLogger(CgProjectScheduleUserImpl.class);
    private final JpaQueryHelper jpaQueryHelper;
    private final ProjectScheduleRepository projectScheduleRepository;
    private final ProjectScheduleUserRepository projectScheduleUserRepository;
    private final ProjectScheduleUserMapper projectScheduleUserMapper;
    private final ProjectScheduleUserDetailsSaveMapper projectScheduleUserDetailsSaveMapper;
    private final ProjectScheduleDetailsSaveMapper projectScheduleDetailsSaveMapper;

    public CgProjectScheduleUserImpl(JpaQueryHelper jpaQueryHelper,
                                     ProjectScheduleRepository projectScheduleRepository,
                                     ProjectScheduleUserRepository projectScheduleUserRepository,
                                     ProjectScheduleUserMapper projectScheduleUserMapper,
                                     ProjectScheduleUserDetailsSaveMapper projectScheduleUserDetailsSaveMapper,
                                     ProjectScheduleDetailsSaveMapper projectScheduleDetailsSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.projectScheduleUserRepository=projectScheduleUserRepository;
        this.projectScheduleUserMapper = projectScheduleUserMapper;
        this.projectScheduleUserDetailsSaveMapper=projectScheduleUserDetailsSaveMapper;
        this.projectScheduleRepository=projectScheduleRepository;
        this.projectScheduleDetailsSaveMapper=projectScheduleDetailsSaveMapper;
    }

    /*@Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgProjectScheduleUserDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        ProjectScheduleUser projectScheduleUser=projectScheduleUserDetailsSaveMapper.toEntity(saveDto);
        if(saveDto.getId() != null){
            ProjectScheduleUser entity = getProjectScheduleUser(saveDto.getId());
            entity.setAttention(saveDto.getAttention());
            projectScheduleUser = projectScheduleUserDetailsSaveMapper.updateEntity(entity,saveDto);
            ProjectSchedule projectSchedule = projectScheduleUser.getProjectSchedule();
            projectSchedule.setProjectScheduleUserId(projectScheduleUser.getId());
            projectSchedule.setAttention(saveDto.getAttention());
            projectScheduleUser.setProjectSchedule(projectSchedule);
        }else{
           *//* ProjectSchedule projectSchedule = projectScheduleUser.getProjectSchedule();
            projectSchedule.setAttention(saveDto.getAttention());
            projectScheduleUser.setProjectSchedule(projectSchedule);*//*
        }
        try{
            ProjectScheduleUser saved = projectScheduleUserRepository.saveAndFlush(projectScheduleUser);
            return saved.getId();
        }catch (Exception e){
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }*/

    @Override
//    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgProjectScheduleDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        ProjectSchedule projectSchedule;
        if(saveDto.getProjectScheduleUser()==null){
            projectSchedule=projectScheduleDetailsSaveMapper.toEntity(saveDto);
            ProjectScheduleUser projectScheduleUser=new ProjectScheduleUser();
            projectScheduleUser.setTenantId(saveDto.getTenantId());
            projectScheduleUser.setAttention(saveDto.getAttention());
            projectSchedule.setProjectScheduleUser(projectScheduleUser);
        }else{
            ProjectSchedule entity=getProjectSchedule(saveDto.getId());
            projectSchedule = projectScheduleDetailsSaveMapper.updateEntity(entity, saveDto);
            projectSchedule.getProjectScheduleUser().setAttention(saveDto.getAttention());
        }
        try{
            //ProjectSchedule newProjectScedule = getProjectSchedule(saveDto.getId());
            ProjectSchedule saved = projectScheduleRepository.saveAndFlush(projectSchedule);
            return saved.getId();
        }catch (Exception e){
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    public CgProjectScheduleUserDto getById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return projectScheduleUserMapper.toDto(getProjectScheduleUser(id));
    }

    protected ProjectScheduleUser getProjectScheduleUser(Integer id){
        Objects.requireNonNull(id);
        List<ProjectScheduleUser> all = projectScheduleUserRepository.findAll(
                ((Specification<ProjectScheduleUser>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if(all.isEmpty()){
            if(!projectScheduleUserRepository.existsById(id)){
                throw entityNotFound(ProjectScheduleUser.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

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

    /*@Override
    public Page<CgProjectScheduleDto> list(CgProjectScheduleQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<ProjectSchedule> result = projectScheduleRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(projectScheduleMapper::toDto);
    }*/

}
