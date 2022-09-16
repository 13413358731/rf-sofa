package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStep;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStepType;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionSup;
import com.realfinance.sofa.cg.core.domain.exec.bid.BaseBiddingDocument;
import com.realfinance.sofa.cg.core.domain.proj.BaseProjectEval.Category;
import com.realfinance.sofa.cg.core.domain.proj.BaseProjectEvalRule;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.domain.proj.ProjectEval;
import com.realfinance.sofa.cg.core.facade.CgProjectExecutionFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.*;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectEvalSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectExecutionDetailsMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectExecutionDetailsSaveMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ProjectExecutionMapper;
import com.realfinance.sofa.cg.core.service.projectexec.ProjectExecutionStepService;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgProjectExecutionFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgProjectExecutionImpl implements CgProjectExecutionFacade {

    private static final Logger log = LoggerFactory.getLogger(CgProjectExecutionImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final ProjectExecutionStepService projectExecutionStepService;
    private final ProjectExecutionRepository projectExecutionRepository;
    private final ProjectEvalRepository projectEvalRepository;
    private final ProjectExecutionStepRepository projectExecutionStepRepository;
    private final ProjectExecutionMapper projectExecutionMapper;
    private final ProjectExecutionDetailsMapper projectExecutionDetailsMapper;
    private final ProjectExecutionDetailsSaveMapper projectExecutionDetailsSaveMapper;
    private final ProjectEvalSaveMapper projectEvalSaveMapper;
    private final BiddingDocumentRepository biddingDocumentRepository;
    private final ProjectRepository projectRepository;

    public CgProjectExecutionImpl(JpaQueryHelper jpaQueryHelper, ProjectExecutionStepService projectExecutionStepService, ProjectExecutionRepository projectExecutionRepository, ProjectEvalRepository projectEvalRepository, ProjectExecutionStepRepository projectExecutionStepRepository, ProjectExecutionMapper projectExecutionMapper, ProjectExecutionDetailsMapper projectExecutionDetailsMapper, ProjectExecutionDetailsSaveMapper projectExecutionDetailsSaveMapper, ProjectEvalSaveMapper projectEvalSaveMapper, BiddingDocumentRepository biddingDocumentRepository, ProjectRepository projectRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.projectExecutionStepService = projectExecutionStepService;
        this.projectExecutionRepository = projectExecutionRepository;
        this.projectEvalRepository = projectEvalRepository;
        this.projectExecutionStepRepository = projectExecutionStepRepository;
        this.projectExecutionMapper = projectExecutionMapper;
        this.projectExecutionDetailsMapper = projectExecutionDetailsMapper;
        this.projectExecutionDetailsSaveMapper = projectExecutionDetailsSaveMapper;
        this.projectEvalSaveMapper = projectEvalSaveMapper;
        this.biddingDocumentRepository = biddingDocumentRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public Page<CgProjectExecutionDto> list(CgProjectExecutionQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<ProjectExecution> result = projectExecutionRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(projectExecutionMapper::toDto);
    }

    @Override
    public CgProjectExecutionDto getById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return projectExecutionMapper.toDto(getProjectExecution(id));
    }

    @Override
    public CgProjectExecutionDetailsDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        CgProjectExecutionDetailsDto cgProjectExecutionDetailsDto = projectExecutionDetailsMapper.toDto(getProjectExecution(id));
        return cgProjectExecutionDetailsDto;
    }

    @Override
    public List<CgProjectExecutionSupDto> getSuppliersById(Integer id) {
        List<ProjectExecutionSup> projectExecutionSups = getProjectExecution(id).getProjectExecutionSups();
        return projectExecutionDetailsMapper.projectExecutionSupListToCgProjectExecutionSupDtoList(projectExecutionSups);
    }

    @Override
    public List<CgProjectExecutionStepDto> getStepsById(Integer id) {
        List<ProjectExecutionStep> projectExecutionSteps = getProjectExecution(id).getProjectExecutionSteps();
        return projectExecutionDetailsMapper.projectExecutionStepListToCgProjectExecutionStepDtoList(projectExecutionSteps);
    }

    @Override
    public List<CgProjectExecutionSupDto> getValidSuppliers(Integer id) {
        List<CgProjectExecutionSupDto> cgProjectExecutionSups = projectExecutionDetailsMapper.projectExecutionSupListToCgProjectExecutionSupDtoList(getProjectExecution(id).getProjectExecutionSups());
        List<CgProjectExecutionSupDto> cgProjectExecSups = new ArrayList<>();
        for (CgProjectExecutionSupDto cgProjectExecutionSup : cgProjectExecutionSups) {
            if(!cgProjectExecutionSup.getModifyMode().equals("TT")){
                cgProjectExecSups.add(cgProjectExecutionSup);
            }
        }
        return cgProjectExecSups;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgProjectExecutionDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        ProjectExecution projectExecution;
        if (saveDto.getId() == null) { // 新增
            throw businessException("不允许新增");
        } else { // 修改
            ProjectExecution entity = getProjectExecution(saveDto.getId());
            projectExecution = projectExecutionDetailsSaveMapper.updateEntity(entity,saveDto);
        }
//        preSave(projectExecution);
        try {
            ProjectExecution saved = projectExecutionRepository.saveAndFlush(projectExecution);
            return saved.getId();
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    public List<String> listPriceEvalRuleProductName(Integer id) {
        List<ProjectEval> projectEvals = projectExecutionRepository.findById(id)
                .map(ProjectExecution::getProject)
                .map(Project::getProjectEvals).orElse(Collections.emptyList());
        List<String> result = projectEvals.stream().filter(e -> e.getCategory() == Category.JGBF)
                .flatMap(e -> e.getProjectEvalRules().stream()).map(BaseProjectEvalRule::getName).collect(Collectors.toList());
        System.out.println(result);
        return result;
    }

    @Override
    @Transactional
    public void startStep(Integer projectExecutionStepId) {
        ProjectExecutionStep projectExecutionStep = projectExecutionStepRepository.findById(Objects.requireNonNull(projectExecutionStepId))
                .orElseThrow(() -> entityNotFound(ProjectExecutionStep.class, "id", projectExecutionStepId));
        if (projectExecutionStep.getStartTime() == null) {
            projectExecutionStep.setStartTime(LocalDateTime.now());
            projectExecutionStepRepository.saveAndFlush(projectExecutionStep);
        }
    }


    @Override
    @Transactional
    public void invalidBid(Integer id, String reason) {
        Objects.requireNonNull(id);
        ProjectExecution projectExecution = getProjectExecution(id);
        projectExecution.setInvalid(true);
        projectExecution.setInvalidReason(reason);
        // TODO: 2021/3/11 听说要把状态记录到方案上
        try {
            projectExecutionRepository.saveAndFlush(projectExecution);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional
    public Integer saveSupplier(Integer id, CgProjectExecutionSupDto dto) {
        ProjectExecution projectExecution = getProjectExecution(id);
        ProjectExecutionSup projectExecutionSup = null;
        if (dto.getId() != null) {
            for (ProjectExecutionSup executionSup : projectExecution.getProjectExecutionSups()) {
                if (executionSup.getId().equals(dto.getId())) {
                    projectExecutionSup = executionSup;
                    break;
                }
            }
        }
        if (projectExecutionSup == null) {
            ProjectExecutionStepType type = null;
            for (ProjectExecutionStep projectExecutionStep : projectExecution.getProjectExecutionSteps()) {
                if (projectExecutionStep.getStartTime() != null && projectExecutionStep.getEndTime() == null) {
                    type = projectExecutionStep.getType();
                    break;
                }
            }
            projectExecutionSup = new ProjectExecutionSup();
            projectExecutionSup.setModifyStep(type != null ? type.getZh() : "");
            projectExecutionSup.setModifyMode(ProjectExecutionSup.ModifyMode.XZ);
            projectExecution.getProjectExecutionSups().add(projectExecutionSup);
        }
        projectExecutionSup.setContactEmail(dto.getContactEmail());
        projectExecutionSup.setContactMobile(dto.getContactMobile());
        projectExecutionSup.setContactName(dto.getContactName());
        projectExecutionSup.setNote(dto.getNote());
        projectExecutionSup.setReason(dto.getReason());
        projectExecutionSup.setSource(dto.getSource());
        projectExecutionSup.setSupplierId(dto.getSupplierId());
        try {
            projectExecutionRepository.saveAndFlush(projectExecution);
        } catch (Exception e) {
            log.error("保存失败",e);
            throw businessException("保存失败");
        }
        return projectExecutionSup.getId();
    }

    @Override
    @Transactional
    public void invalidSupplier(Integer id, Integer projectExecutionSupId) {
        ProjectExecution projectExecution = getProjectExecution(id);
        for (ProjectExecutionSup projectExecutionSup : projectExecution.getProjectExecutionSups()) {
            if (projectExecutionSup.getId().equals(projectExecutionSupId)) {
                projectExecutionSup.setModifyMode(ProjectExecutionSup.ModifyMode.TT);
                try {
                    projectExecutionRepository.saveAndFlush(projectExecution);
                } catch (Exception e) {
                    log.error("保存失败",e);
                    throw businessException("保存失败");
                }
                break;
            }
        }
    }

    @Override
    @Transactional
    public void endStep(Integer projectExecutionStepId) {
        ProjectExecutionStep projectExecutionStep = projectExecutionStepRepository.findById(Objects.requireNonNull(projectExecutionStepId))
                .orElseThrow(() -> entityNotFound(ProjectExecutionStep.class, "id", projectExecutionStepId));
        Integer projectExecutionId = projectExecutionStep.getProjectExecution().getId();  //方案执行Id
        ProjectExecutionStep step = projectExecutionStepRepository.getOne(projectExecutionStepId);
        Integer biddingId = biddingDocumentRepository.findByProjectExecution(projectExecutionRepository.getOne(projectExecutionId))
                .map(BaseBiddingDocument::getId).orElse(null);    //获取制作标书Id
        if (step.getType() == Enum.valueOf(ProjectExecutionStepType.class,"ZBGG")){
            biddingId = 1;
        }
        if (projectExecutionStep.getEndTime() == null && biddingId > 0) {
            projectExecutionStep.setEndTime(LocalDateTime.now());
            projectExecutionStepRepository.saveAndFlush(projectExecutionStep);
        } else if(biddingId==null){
            throw businessException("请制作标书！");
        }
    }


    protected ProjectExecution getProjectExecution(Integer id) {
        Objects.requireNonNull(id);
        List<ProjectExecution> all = projectExecutionRepository.findAll(
                ((Specification<ProjectExecution>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!projectExecutionRepository.existsById(id)) {
                throw entityNotFound(ProjectExecution.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String,String> listProjectExecutionId(Integer projectId) {
        Optional<Project> byId = projectRepository.findById(projectId);
        ProjectExecution projectExecution = projectExecutionRepository.findByProject(byId.get());
        Map<String,String> map =new HashMap<>();
        map.put("createdUserId",byId.get().getCreatedUserId().toString());
        map.put("projectExecutionId",projectExecution.getId().toString());
        map.put("name",byId.get().getName());
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveEval(CgProjectEvalDto saveDto) {
        Objects.requireNonNull(saveDto);
        ProjectEval projectEval = new ProjectEval();
        if (saveDto.getId() == null) { // 新增
            System.out.println("不允许新增");
        } else { // 修改
            ProjectEval entity = getEval(saveDto.getId());
            saveDto.getProjectEvalRules().forEach(e->e.setSubCode(UUID.randomUUID().toString()));
            projectEval = projectEvalSaveMapper.updateEntity(entity,saveDto);
        }
        try {
            ProjectEval saved = projectEvalRepository.saveAndFlush(projectEval);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected ProjectEval getEval(Integer id) {
        Objects.requireNonNull(id);
        List<ProjectEval> all = projectEvalRepository.findAll(
                ((Specification<ProjectEval>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!projectEvalRepository.existsById(id)) {
                System.out.println("找不到相应专家");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }
}
