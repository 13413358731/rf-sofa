package com.realfinance.sofa.cg.sup.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.sup.domain.BusinessProject;
import com.realfinance.sofa.cg.sup.domain.BusinessReply;
import com.realfinance.sofa.cg.sup.domain.BusinessReplyAttD;
import com.realfinance.sofa.cg.sup.facade.CgBusinessProjectFacade;
import com.realfinance.sofa.cg.sup.model.CgAttachmentDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectQueryCriteria;
import com.realfinance.sofa.cg.sup.model.CgResultNoticeAchmentDto;
import com.realfinance.sofa.cg.sup.repository.BusinessProjectRepository;
import com.realfinance.sofa.cg.sup.service.mapstruct.BusinessProjectDetailsMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.BusinessProjectMapper;
import com.realfinance.sofa.cg.sup.service.mapstruct.BusinessProjectSaveMapper;
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
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.dataAccessForbidden;
import static com.realfinance.sofa.cg.sup.util.ExceptionUtils.entityNotFound;
import static com.realfinance.sofa.cg.sup.util.QueryCriteriaUtils.toSpecification;

@Service
@SofaService(interfaceType = CgBusinessProjectFacade.class, uniqueId = "${service.rf-cg-sup.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgBusinessProjectImpl implements CgBusinessProjectFacade {

    private static final Logger log = LoggerFactory.getLogger(CgBusinessProjectImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final BusinessProjectRepository businessProjectRepository;
    private final BusinessProjectMapper businessProjectMapper;
    private final BusinessProjectDetailsMapper businessProjectDetailsMapper;
    private final BusinessProjectSaveMapper businessProjectSaveMapper;

    public CgBusinessProjectImpl(JpaQueryHelper jpaQueryHelper, BusinessProjectRepository businessProjectRepository, BusinessProjectMapper businessProjectMapper, BusinessProjectDetailsMapper businessProjectDetailsMapper, BusinessProjectSaveMapper businessProjectSaveMapper) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.businessProjectRepository = businessProjectRepository;
        this.businessProjectMapper = businessProjectMapper;
        this.businessProjectDetailsMapper = businessProjectDetailsMapper;
        this.businessProjectSaveMapper = businessProjectSaveMapper;
    }

    @Override
    public Page<CgBusinessProjectDto> list(CgBusinessProjectQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<BusinessProject> result = businessProjectRepository.findAll(toSpecification(queryCriteria), pageable);
        return result.map(businessProjectMapper::toDto);
    }

    @Override
    public List<CgBusinessProjectDto> list(CgBusinessProjectQueryCriteria queryCriteria) {
        List<BusinessProject> result = businessProjectRepository.findAll(toSpecification(queryCriteria));
        return result.stream().map(businessProjectMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CgBusinessProjectDetailsDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        CgBusinessProjectDetailsDto cgBusinessProjectDetailsDto = businessProjectDetailsMapper.toDto(getBusinessProject(id));
        return cgBusinessProjectDetailsDto;
    }

    @Override
    @Transactional
    public Integer save(String projectId, String projectNo, String projectName) {
        Objects.requireNonNull(projectNo);
        Objects.requireNonNull(projectName);
        String tenantId = DataScopeUtils.loadTenantId();
        BusinessProject businessProject = businessProjectRepository.findByProjectIdAndTenantId(projectId, tenantId)
                .map(e -> {
                    e.setProjectName(projectName);
                    return e;
                })
                .orElseGet(() -> {
                    BusinessProject newBusinessProject = new BusinessProject();
                    newBusinessProject.setProjectId(projectId);
                    newBusinessProject.setProjectNo(projectNo);
                    newBusinessProject.setProjectName(projectName);
                    newBusinessProject.setProjectStatus("");
                    newBusinessProject.setTenantId(tenantId);
                    return newBusinessProject;
                });
        try {
            BusinessProject saved = businessProjectRepository.saveAndFlush(businessProject);
            return saved.getId();
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional
    public Integer save(CgBusinessProjectDetailsDto businessProject,List<CgAttachmentDto> dtoList) {
        BusinessProject entity;
        entity = businessProjectSaveMapper.toEntity(businessProject);
        try {
            List<BusinessReply> replies = entity.getReplies();
            for(BusinessReply reply:replies){
                if(dtoList.size()!=0){
                    List<BusinessReplyAttD> businessReplyAttDS = sealBusinessReplyAttD(dtoList);
                    reply.setAttDs(businessReplyAttDS);
                }
            }
            BusinessProject saved = businessProjectRepository.saveAndFlush(entity);
            return saved.getId();
        } catch (Exception e) {
            log.error("商业项目保存失败",e);
            throw businessException("保存失败");
        }
    }

    private List<BusinessReplyAttD> sealBusinessReplyAttD(List<CgAttachmentDto> attDs) {
        return attDs.stream().map(a -> {
            BusinessReplyAttD businessReplyAttD = new BusinessReplyAttD();
            businessReplyAttD.setCategory(null);
            businessReplyAttD.setExt(a.getExt());
            businessReplyAttD.setName(a.getName());
            businessReplyAttD.setNote(a.getNote());
            businessReplyAttD.setPath(a.getPath());
            businessReplyAttD.setSize(a.getSize());
            businessReplyAttD.setUploadTime(a.getUploadTime());
            return businessReplyAttD;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateProjectStatus(String projectId, String projectStatus) {
        Objects.requireNonNull(projectId);
        Objects.requireNonNull(projectStatus);
        String tenantId = DataScopeUtils.loadTenantId();
        BusinessProject businessProject = businessProjectRepository.findByProjectIdAndTenantId(projectId, tenantId)
                .orElseThrow(() -> entityNotFound(BusinessProject.class, "projectId", projectId));
        businessProject.setProjectStatus(projectStatus);
        try {
            businessProjectRepository.saveAndFlush(businessProject);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }


    @Override
    public void delete(Set<String> projectIds) {
        Objects.requireNonNull(projectIds);
        String tenantId = DataScopeUtils.loadTenantId();
        if (projectIds.isEmpty()) {
            return;
        }
        List<BusinessProject> toDelete = businessProjectRepository.findByProjectIdInAndTenantId(projectIds,tenantId);
        if (toDelete.isEmpty()) {
            return;
        }
        try {
            businessProjectRepository.deleteAll(toDelete);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected BusinessProject getBusinessProject(Integer id) {
        Objects.requireNonNull(id);
        List<BusinessProject> all = businessProjectRepository.findAll(
                ((Specification<BusinessProject>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!businessProjectRepository.existsById(id)) {
                System.out.println("找不到相应专家");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }
}
