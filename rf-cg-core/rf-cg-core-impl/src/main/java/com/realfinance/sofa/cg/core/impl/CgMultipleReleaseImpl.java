package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionAtt;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStepType;
import com.realfinance.sofa.cg.core.domain.exec.release.MultipleRelease;
import com.realfinance.sofa.cg.core.domain.exec.release.MultipleReleaseAtt;
import com.realfinance.sofa.cg.core.facade.CgMultipleReleaseFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.MultipleReleaseRepository;
import com.realfinance.sofa.cg.core.repository.ProjectExecutionRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.MultipleReleaseDetailsMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.MultipleReleaseDetailsSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.MultipleReleaseMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectExecutionDetailsSaveMapper;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgMultipleReleaseFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgMultipleReleaseImpl implements CgMultipleReleaseFacade {

    private static final Logger log = LoggerFactory.getLogger(CgMultipleReleaseImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final MultipleReleaseRepository multipleReleaseRepository;
    private final ProjectExecutionDetailsSaveMapper projectExecutionDetailsSaveMapper;
    private final MultipleReleaseMapper multipleReleaseMapper;
    private final MultipleReleaseDetailsMapper multipleReleaseDetailsMapper;
    private final MultipleReleaseDetailsSaveMapper multipleReleaseDetailsSaveMapper;
    private final ProjectExecutionRepository projectExecutionRepository;

    public CgMultipleReleaseImpl(JpaQueryHelper jpaQueryHelper, MultipleReleaseRepository multipleReleaseRepository, ProjectExecutionDetailsSaveMapper projectExecutionDetailsSaveMapper, MultipleReleaseMapper multipleReleaseMapper, MultipleReleaseDetailsMapper multipleReleaseDetailsMapper, MultipleReleaseDetailsSaveMapper multipleReleaseDetailsSaveMapper, ProjectExecutionRepository projectExecutionRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.multipleReleaseRepository = multipleReleaseRepository;
        this.projectExecutionDetailsSaveMapper = projectExecutionDetailsSaveMapper;
        this.multipleReleaseMapper = multipleReleaseMapper;
        this.multipleReleaseDetailsMapper = multipleReleaseDetailsMapper;
        this.multipleReleaseDetailsSaveMapper = multipleReleaseDetailsSaveMapper;
        this.projectExecutionRepository = projectExecutionRepository;
    }

    @Override
    public Page<CgMultipleReleaseDto> list(CgMultipleReleaseQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<MultipleRelease> result = multipleReleaseRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(multipleReleaseMapper::toDto);
    }

    @Override
    public CgMultipleReleaseDto getById(Integer id) {
        Objects.requireNonNull(id);
        return multipleReleaseMapper.toDto(getMultipleRelease(id));
    }

    @Override
    public CgMultipleReleaseDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        MultipleRelease multipleRelease = getMultipleRelease(id);
        CgMultipleReleaseDetailsDto result = multipleReleaseDetailsMapper.toDto(multipleRelease);
//        List<ProjectExecutionAtt> atts = multipleRelease.getProjectExecution().getProjectExecutionAtts().stream()
//                .filter(e -> isCurrentAtt(id, e)).collect(Collectors.toList());
//                .map(projectExecutionDetailsSaveMapper::cgProjectExecutionAttToProjectExecutionAttDto)
//                .collect(Collectors.toList());
//        result.setMultipleReleaseAtts(atts);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgMultipleReleaseDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        MultipleRelease multipleRelease;
        if (saveDto.getId() == null) { // 新增
            multipleRelease = multipleReleaseDetailsSaveMapper.toEntity(saveDto);
            LocalDateTime now = LocalDateTime.now();
            multipleRelease.setReplyEndTime(now);
            multipleRelease.setOpenStartTime(now);
            multipleRelease.setOpenEndTime(now);
            multipleRelease.setName(saveDto.getName());
            multipleRelease.setContent(saveDto.getContent());
            multipleRelease.setNeedQuote(saveDto.getNeedQuote());
            multipleRelease.setStatus(FlowStatus.EDIT);
            multipleRelease.setTenantId(DataScopeUtils.loadTenantId());
            multipleRelease.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
        } else { // 修改
            MultipleRelease entity = getMultipleRelease(saveDto.getId());
            multipleRelease = multipleReleaseDetailsSaveMapper.updateEntity(entity,saveDto);
        }
//        preSave(multipleRelease);
        try {
            MultipleRelease saved = multipleReleaseRepository.saveAndFlush(multipleRelease);
            saveAtt(saveDto, saved);
            return saved.getId();
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);

        MultipleRelease multipleRelease = multipleReleaseRepository.findById(id)
                .orElseThrow(() -> entityNotFound(MultipleRelease.class,"id",id));

        FlowStatus currentStatusEnum = multipleRelease.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id,status);
            }
            return;
        }
        multipleRelease.setStatus(statusEnum);
        if (statusEnum == PASS) {
            handlePass(multipleRelease);
        }
        try {
            multipleReleaseRepository.saveAndFlush(multipleRelease);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败",e);
            }
            throw businessException("更新状态失败",e);
        }
    }

    private boolean isCurrentAtt(Integer id, ProjectExecutionAtt e) {
        return Objects.equals(e.getStepType(), ProjectExecutionStepType.SWCQ)
                && Objects.equals(e.getStepDataId(), id);
    }

    private void saveAtt(CgMultipleReleaseDetailsSaveDto saveDto, MultipleRelease multipleRelease) {
        List<ProjectExecutionAtt> atts = multipleRelease.getProjectExecution().getProjectExecutionAtts()
                .stream().filter(e -> isCurrentAtt(multipleRelease.getId(), e)).collect(Collectors.toList());
        List<CgProjectExecutionAttDto> multipleReleaseAtts = saveDto.getMultipleReleaseAtts();
        if (multipleReleaseAtts != null) {
            for (CgProjectExecutionAttDto biddingDocumentAtt : multipleReleaseAtts) {
                ProjectExecutionAtt projectExecutionAtt = null;
                if (biddingDocumentAtt.getId() != null) {
                    for (ProjectExecutionAtt att : atts) {
                        if (att.getId().equals(biddingDocumentAtt.getId())) {
                            projectExecutionDetailsSaveMapper.updateAtt(att,biddingDocumentAtt);
                            break;
                        }
                    }
                } else {
                    projectExecutionAtt = projectExecutionDetailsSaveMapper
                            .cgProjectExecutionAttDtoToProjectExecutionAtt(biddingDocumentAtt);
                    projectExecutionAtt.setStepType(ProjectExecutionStepType.SWCQ);
                    projectExecutionAtt.setStepDataId(multipleRelease.getId());
                    projectExecutionAtt.setEncrypted(false);
                    atts.add(projectExecutionAtt);
                }
            }
        }
    }

    private void handlePass(MultipleRelease multipleRelease) {
        multipleRelease.setPassTime(LocalDateTime.now());
        // TODO: 2021/3/10 保存历史记录
    }

    @Transactional
    protected void preSave(MultipleRelease multipleRelease) {
        ProjectExecution entity = multipleRelease.getProjectExecution();
        List<ProjectExecutionAtt> projectExecutionAtts = entity.getProjectExecutionAtts();
        List<MultipleReleaseAtt> multipleReleaseAtts = multipleRelease.getMultipleReleaseAtts();
        for (MultipleReleaseAtt multipleReleaseAtt : multipleReleaseAtts) {
            ProjectExecutionAtt projectExecutionAtt = new ProjectExecutionAtt();
            projectExecutionAtt.setCreatedTime(multipleReleaseAtt.getCreatedTime());
            projectExecutionAtt.setCreatedUserId(multipleReleaseAtt.getCreatedUserId());
            projectExecutionAtt.setModifiedTime(multipleReleaseAtt.getModifiedTime());
            projectExecutionAtt.setModifiedUserId(multipleReleaseAtt.getModifiedUserId());
            projectExecutionAtt.setExt(multipleReleaseAtt.getExt());
            projectExecutionAtt.setName(multipleReleaseAtt.getName());
            projectExecutionAtt.setPath(multipleReleaseAtt.getPath());
            projectExecutionAtt.setSize(multipleReleaseAtt.getSize());
            projectExecutionAtt.setSource(multipleReleaseAtt.getSource());
            projectExecutionAtt.setUploadTime(multipleReleaseAtt.getUploadTime());
            projectExecutionAtt.setStepType(ProjectExecutionStepType.SWCQ);
            projectExecutionAtt.setStepDataId(multipleReleaseAtt.getId());
            projectExecutionAtts.add(projectExecutionAtt);
        }
        entity.setProjectExecutionAtts(projectExecutionAtts);
        try {
            ProjectExecution saved = projectExecutionRepository.saveAndFlush(entity);
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
    protected MultipleRelease getMultipleRelease(Integer id) {
        Objects.requireNonNull(id);
        List<MultipleRelease> all = multipleReleaseRepository.findAll(
                ((Specification<MultipleRelease>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!multipleReleaseRepository.existsById(id)) {
                throw entityNotFound(MultipleRelease.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }
}
