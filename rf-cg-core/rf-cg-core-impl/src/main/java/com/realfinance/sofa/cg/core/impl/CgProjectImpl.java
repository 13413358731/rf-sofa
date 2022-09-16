package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.BaseDrawExpertRule;
import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.cg.core.domain.ProjectCategory;
import com.realfinance.sofa.cg.core.domain.PurchaseMode;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionAtt;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStep;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionSup;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionSup.ModifyMode;
import com.realfinance.sofa.cg.core.domain.meeting.Meeting;
import com.realfinance.sofa.cg.core.domain.proj.*;
import com.realfinance.sofa.cg.core.domain.req.BaseRequirement;
import com.realfinance.sofa.cg.core.domain.req.Requirement;
import com.realfinance.sofa.cg.core.domain.req.RequirementSup;
import com.realfinance.sofa.cg.core.domain.serialno.SerialNumberRecordId;
import com.realfinance.sofa.cg.core.facade.CgProjectFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.*;
import com.realfinance.sofa.cg.core.service.evalformula.EvalFormulaFactory;
import com.realfinance.sofa.cg.core.service.mapstruct.*;
import com.realfinance.sofa.cg.core.service.serialno.SerialNumberService;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.sdebank.SdebankEquityPenetration;
import com.realfinance.sofa.sdebank.model.ElementBusIInfoDto;
import com.realfinance.sofa.sdebank.model.EquityPenetrationDto;
import com.realfinance.sofa.sdebank.model.SupplierRelationshipDto;
import com.realfinance.sofa.sdebank.response.TokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.realfinance.sofa.cg.core.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionStepType.*;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgProjectImpl implements CgProjectFacade {
    private static final Logger log = LoggerFactory.getLogger(CgProjectImpl.class);

    private final SerialNumberService serialNumberService;
    private final JpaQueryHelper jpaQueryHelper;
    private final ProjectRepository projectRepository;
    private final ProjectExecutionRepository projectExecutionRepository;
    private final HistoricProjectRepository historicProjectRepository;
    private final DrawExpertRepository drawExpertRepository;
    private final MeetingRepository meetingRepository;
    private final ProjectMapper projectMapper;
    private final ProjectSmallMapper projectSmallMapper;
    private final ProjectDetailsMapper projectDetailsMapper;
    private final ProjectDetailsSaveMapper projectDetailsSaveMapper;
    private final RequirementDetailsMapper requirementDetailsMapper;
    private final HistoricProjectDetailsMapper historicProjectDetailsMapper;
    private final ProjectRelationshipRepository projectRelationshipRepository;
    private final ProjectRelationshipMapper relationshipMapper;
    private final ProjectSupRepository projectSupRepository;
    private final ProjectSupMapper projectSupMapper;

    public CgProjectImpl(SerialNumberService serialNumberService, JpaQueryHelper jpaQueryHelper, ProjectRepository projectRepository, ProjectExecutionRepository projectExecutionRepository, HistoricProjectRepository historicProjectRepository, DrawExpertRepository drawExpertRepository, MeetingRepository meetingRepository, ProjectMapper projectMapper, ProjectSmallMapper projectSmallMapper, ProjectDetailsMapper projectDetailsMapper, ProjectDetailsSaveMapper projectDetailsSaveMapper, RequirementDetailsMapper requirementDetailsMapper, HistoricProjectDetailsMapper historicProjectDetailsMapper, ProjectRelationshipRepository projectRelationshipRepository, ProjectRelationshipMapper relationshipMapper, ProjectSupRepository projectSupRepository, ProjectSupMapper projectSupMapper) {
        this.serialNumberService = serialNumberService;
        this.jpaQueryHelper = jpaQueryHelper;
        this.projectRepository = projectRepository;
        this.projectExecutionRepository = projectExecutionRepository;
        this.historicProjectRepository = historicProjectRepository;
        this.drawExpertRepository = drawExpertRepository;
        this.meetingRepository = meetingRepository;
        this.projectMapper = projectMapper;
        this.projectSmallMapper = projectSmallMapper;
        this.projectDetailsMapper = projectDetailsMapper;
        this.projectDetailsSaveMapper = projectDetailsSaveMapper;
        this.requirementDetailsMapper = requirementDetailsMapper;
        this.historicProjectDetailsMapper = historicProjectDetailsMapper;
        this.projectRelationshipRepository = projectRelationshipRepository;
        this.relationshipMapper = relationshipMapper;
        this.projectSupRepository = projectSupRepository;
        this.projectSupMapper = projectSupMapper;
    }

    @Override
    public Page<CgProjectDto> list(CgProjectQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<Project> result = projectRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(projectMapper::toDto);
    }

    @Override
    public CgProjectDto getById(Integer id) {
        Objects.requireNonNull(id);
        return projectMapper.toDto(getProject(id));
    }

    @Override
    public CgProjectDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        return projectDetailsMapper.toDto(getProject(id));
    }

    @Override
    public List<CgProjectDetailsDto> listHistoricDetailsById(Integer id, Boolean all){
        Objects.requireNonNull(id);
        Objects.requireNonNull(all);
        Project project = getProject(id);
        Sort sort = Sort.by(Direction.DESC, "createdTime");
        List<CgProjectDetailsDto> result=new ArrayList<>();
        result.add(projectDetailsMapper.toDto(getProject(id)));
        if(!all){
            Page<HistoricProject> page = historicProjectRepository.
                    findByProject(project, PageRequest.of(0, 1, sort));
            if(!page.isEmpty()){
                result.addAll(page.map(historicProjectDetailsMapper::toDto).getContent());
            }
        }else{
            List<HistoricProject> allHistoricProject = historicProjectRepository.findByProject(project, sort);
            if(!allHistoricProject.isEmpty()){
                result.addAll(allHistoricProject.stream().map(historicProjectDetailsMapper::toDto).collect(Collectors.toList()));
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgProjectDetailsSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        Project project;
        boolean projectCategoryChanged = false;
        if (saveDto.getId() == null) { // 新增
            throw businessException("不允许新增");
        } else { // 修改
            Project entity = getProject(saveDto.getId());
            ProjectCategory projectCategory = entity.getProjectCategory();
            project = projectDetailsSaveMapper.updateEntity(entity,saveDto);
            projectCategoryChanged = projectCategory != project.getProjectCategory();
        }
        preSave(project);
        if (projectCategoryChanged) {
            // 如果项目类别发生改变，生成项目编码
            generateProjectNo(project);
        }
        try {
            Project saved = projectRepository.saveAndFlush(project);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Project.class,"id",id));

        FlowStatus currentStatusEnum = project.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id,status);
            }
            return;
        }
        project.setStatus(statusEnum);
        if (statusEnum == PASS) {
            handlePass(project);
        }
        try {
            projectRepository.saveAndFlush(project);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败",e);
            }
            throw businessException("更新状态失败",e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnRequirement(Integer id, String reason) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(reason);
        Project project = getProject(id);
        Requirement requirement = project.getRequirement();
        if (requirement == null) {
            throw businessException("找不到采购需求申请");
        }
        requirement.setAcceptStatus(BaseRequirement.AcceptStatus.FATH);
        requirement.setStatus(FlowStatus.EDIT);
        project.setReturnReq(true);
        try {
            projectRepository.saveAndFlush(project);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("退回到采购申请失败",e);
            }
            throw businessException("更新状态失败",e);
        }
    }

    @Override
    public CgRequirementDetailsDto getRequirementDetailsById(Integer id) {
        Objects.requireNonNull(id);
        Project project = getProject(id);
        Requirement requirement = project.getRequirement();
        return requirementDetailsMapper.toDto(requirement);
    }

    @Override
    public List<CgPriceEvalFormulaDto> listPriceEvalFormula() {
        return EvalFormulaFactory.listEvalFormula().stream().map(e -> {
            CgPriceEvalFormulaDto cgPriceEvalFormulaDto = new CgPriceEvalFormulaDto();
            cgPriceEvalFormulaDto.setEvalFormulaName(e.getName());
            cgPriceEvalFormulaDto.setEvalFormula(e.getFormula());
            return cgPriceEvalFormulaDto;
        }).collect(Collectors.toList());
    }

    @Override
    public Page<CgProjectSmallDto> queryRefer(CgProjectQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<Project> result = projectRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(projectSmallMapper::toDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgProjectRelationshipDto> relationship(Integer id, List<CgProjectRelationshipDto> list) {
        Optional<Project> byId = projectRepository.findById(id);
        //查询子表(供应商关联关系表是否存有上次关联数据)
        List<ProjectRelationship> byProject = projectRelationshipRepository.findByProject(byId.get());
        if (byProject.size() != 0) {
            projectRelationshipRepository.deleteByProject(byId.get());
        }
        SdebankEquityPenetration sd = new SdebankEquityPenetration();
        TokenResponse token = sd.getToken();
        List<EquityPenetrationDto> dtoList = new ArrayList<>();
        List<ElementBusIInfoDto> elementBusIInfoDtos = new ArrayList<>();
        list.forEach(e -> {
            EquityPenetrationDto dto = new EquityPenetrationDto();
            dto.setName(e.getName());
            if (e.getUnifiedSocialCreditCode() != null && e.getUnifiedSocialCreditCode() != "") {
                dto.setUnifiedSocialCreditCode(e.getUnifiedSocialCreditCode());
            }
            dtoList.add(dto);
        });
        dtoList.forEach(e -> {
            ElementBusIInfoDto elementBusIInfoDto = sd.elementBusIInfo(token, e);
            elementBusIInfoDtos.add(elementBusIInfoDto);
        });
        //筛选出 满足1-5条件的供应商并保存到数据库中
        List<ProjectRelationship> saveList = new ArrayList<>();
        //关联序号
        Integer number = 1;
        List<List<ElementBusIInfoDto>> lists = new ArrayList<>();
        if (elementBusIInfoDtos.size() > 2) {
            lists = getLists(elementBusIInfoDtos);
        } else {
            lists.add(elementBusIInfoDtos);
        }
        for (List<ElementBusIInfoDto> busIInfoDtos : lists) {
            SupplierRelationshipDto dto = sd.SupplierRelationship(token, busIInfoDtos);
            if (dto.getType() != null) {
                for (ElementBusIInfoDto elementBusIInfoDto : dto.getList()) {
                    ProjectRelationship relationship = getRelationship(elementBusIInfoDto);
                    relationship.setType(dto.getType());
                    relationship.setProject(byId.get());
                    relationship.setNumber(number);
                    if (dto.getProportion() != null) {
                        relationship.setProportion(dto.getProportion());
                    }
                    saveList.add(relationship);
                }
                number++;
            }
        }
        if (saveList.size() == 0) {
            return null;
        }
        List<ProjectRelationship> projectRelationships = projectRelationshipRepository.saveAll(saveList);
        List<CgProjectRelationshipDto> returnList = relationshipMapper.toDtoList(projectRelationships);
        return returnList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgProjectSupDto> updateProjectSupRelatedStatus(List<CgProjectSupDto> dtos) {
        List<Integer> ids=dtos.stream().map(CgProjectSupDto::getId).collect(Collectors.toList());
        Map<Integer,CgProjectSupDto> map=dtos.stream().collect(HashMap::new,(m,v)->m.put(v.getId(),v),HashMap::putAll);
        List<ProjectSup> allById = projectSupRepository.findAllById(ids);
        if (allById.size()==0){
            throw new RuntimeException("传入数据异常!");
        }
        for (ProjectSup projectSup : allById) {
            projectSup.setSupplierRelatedStatus(map.get(projectSup.getId()).getSupplierRelatedStatus());
        }
        //修改采购申请下子表供应商的数据
        List<ProjectSup> sups = projectSupRepository.saveAll(allById);
        List<CgProjectSupDto> supDtos = projectSupMapper.toDtoList(sups);
        return supDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgProjectSupDto> updateProjectSupCreditStatus(List<CgProjectSupDto> dtos) {
        List<Integer> ids=dtos.stream().map(CgProjectSupDto::getId).collect(Collectors.toList());
        Map<Integer,CgProjectSupDto> map=dtos.stream().collect(HashMap::new,(m,v)->m.put(v.getId(),v),HashMap::putAll);
        List<ProjectSup> allById = projectSupRepository.findAllById(ids);
        for (ProjectSup projectSup : allById) {
            projectSup.setSupplierCreditStatus(map.get(projectSup.getId()).getSupplierCreditStatus());
            projectSup.setSupplierCreditTime(LocalDateTime.now());

        }
        //修改采购申请下子表供应商的数据
        List<ProjectSup> sups = projectSupRepository.saveAll(allById);
        List<CgProjectSupDto> supDtos = projectSupMapper.toDtoList(sups);
        return supDtos;
    }


    /**
     * 供应商 匹配方法
     *
     * @param list
     * @return
     */
    protected List<List<ElementBusIInfoDto>> getLists(List<ElementBusIInfoDto> list) {
        List<List<ElementBusIInfoDto>> result = new ArrayList<>();  //用来存放子集的集合，如{{},{1},{2},{1,2}}
        int length = list.size();
        int num = length == 0 ? 0 : 1 << (length);  //2的n次方，若集合set为空，num为0；若集合set有4个元素，那么num为16.

        //从0到2^n-1（[00...00]到[11...11]）
        for (int i = 0; i < num; i++) {
            List<ElementBusIInfoDto> subSet = new ArrayList<>();
            int index = i;
            for (int j = 0; j < length; j++) {
                if ((index & 1) == 1) {    //每次判断index最低位是否为1，为1则把集合set的第j个元素放到子集中
                    subSet.add(list.get(j));
                }
                index >>= 1;    //右移一位
            }
            if (subSet.size() == 2) {
                result.add(subSet);    //把子集存储起来
            }
        }
        return result;
    }

    /**
     * 供应商关联关系表
     * 部分字段获取
     *
     * @param dto 比较出供应商有关联的数据
     * @return
     */
    protected ProjectRelationship getRelationship(ElementBusIInfoDto dto) {
        ProjectRelationship relationship = new ProjectRelationship();
        relationship.setName(dto.getName());
        relationship.setUnifiedSocialCreditCode(dto.getUnifiedSocialCreditCode());
        relationship.setStatutoryRepresentative(dto.getStatutoryRepresentative());
        relationship.setShareholderNames(getString(dto.getShareholderNames()));
        relationship.setShareholderTypes(getString(dto.getShareholderTypes()));
        relationship.setEquityRatio(getString(dto.getEquityRatio()));
        return relationship;
    }
    /**
     * list集合转String
     * @param list
     * @return
     */
    protected String getString(Collection list){
        if (list==null || list.size()==0){
            return null;
        }
        String str = StringUtils.substringBeforeLast(
                StringUtils.substringAfterLast(list.toString(), "["), "]");
        return str;
    }


    protected void preSave(Project project) {
        if (project.getStatus() == PASS) {
            throw businessException("流程已通过，不能修改");
        }
        // 检查评分大项表
        List<ProjectEval> projectEvals = project.getProjectEvals();
        if (projectEvals != null && !projectEvals.isEmpty()) {
            BigDecimal sum = BigDecimal.ZERO;
            Set<BaseProjectEval.Category> categorySet = new HashSet<>();
            for (ProjectEval projectEval : projectEvals) {
                sum = sum.add(projectEval.getWeight());
                if (!categorySet.add(projectEval.getCategory())) {
                    throw businessException("评分类别重复");
                }
            }
            if (sum.intValue() != 100) {
                throw businessException("权重之和必须为100");
            }
        }
        //赋值当前登录人
        if (project.getProjectAtts().size()!=0){
            for (ProjectAtt projectAtt : project.getProjectAtts()) {
                if (projectAtt.getUploader()==null){
                    projectAtt.setUploader(RpcUtils.getPrincipalId().get());
                }
            }
        }
        // 检查评分细则


        // 重新计算市场总价
//        project.resetMarketPrice();
    }

    /**
     * 通过处理
     * @param project
     */
    protected void handlePass(Project project) {
        Integer projExecId = generateProjectExecution(project);
        generateMeeting(project,projExecId);
        saveHistory(project);
    }

    /**
     * 生成项目编码
     * @param project
     */
    protected void generateProjectNo(Project project) {
        ProjectCategory projectCategory = project.getProjectCategory();
        if (log.isDebugEnabled()) {
            log.debug("生成采购方案项目编码，项目类别：{}",projectCategory);
        }
        if (projectCategory == null) {
            // 忽略
        } else {
            String no = serialNumberService.next(SerialNumberRecordId
                            .of("PROJECT", DataScopeUtils.loadTenantId()),
                    Collections.singletonMap("projectCategory",projectCategory.getCode()));
            project.setProjectNo("SDG"+projectCategory.getCode()+no);
        }
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected Project getProject(Integer id) {
        Objects.requireNonNull(id);
        List<Project> all = projectRepository.findAll(
                ((Specification<Project>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!projectRepository.existsById(id)) {
                throw entityNotFound(Project.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 生成采购方案执行
     * @param project
     */
    protected Integer generateProjectExecution(Project project) {
        ProjectExecution projectExecution = new ProjectExecution();
        projectExecution.setDepartmentId(project.getDepartmentId());
        projectExecution.setTenantId(project.getTenantId());
        projectExecution.setInvalid(false);
        projectExecution.setProject(project);
        projectExecution.setReturnReq(false);
        // 动态生成执行环节
        /**
         * 1.采购方式为公开招标(GKZB): 1.招标公告 2.审查设置 3.发文与应答 4.唱标 5.抽取专家 6.评审 7.澄清与报价  8.结果审批  9.中标结果公告 10.结果通知
         * 2.采购方式为邀请招标(YQZB): 1.审查设置 2.发文与应答 3.唱标 4.抽取专家 5.评审 6.澄清与报价  7.结果审批 8.结果通知
         * 3.采购方式为竞争性谈判(JZXTP)、竞争性磋商(JZXCS)、询价(XJ)、单一来源(DYLY): 1.发文与应答 2.抽取专家 3.评审 4.澄清与报价  5.结果审批
         */
        List<ProjectExecutionStep> projectExecutionSteps=null;
        if(project.getPurMode() == PurchaseMode.GKZB){
            projectExecutionSteps= Stream.of(ZBGG,ZZBS, FBYD, CB, CQZJ, HY, SWCQ, JC,ZBJGGG,JGTZ).map(e -> {
                ProjectExecutionStep projectExecutionStep = new ProjectExecutionStep();
                projectExecutionStep.setType(e);
                return projectExecutionStep;
            }).collect(Collectors.toList());
        }else if(project.getPurMode() == PurchaseMode.YQZB){
            projectExecutionSteps= Stream.of(ZZBS, FBYD, CB, CQZJ, HY, SWCQ, JC,JGTZ).map(e -> {
                ProjectExecutionStep projectExecutionStep = new ProjectExecutionStep();
                projectExecutionStep.setType(e);
                return projectExecutionStep;
            }).collect(Collectors.toList());
        }else{
            projectExecutionSteps= Stream.of(FBYD, CQZJ, HY, SWCQ, JC).map(e -> {
                ProjectExecutionStep projectExecutionStep = new ProjectExecutionStep();
                projectExecutionStep.setType(e);
                return projectExecutionStep;
            }).collect(Collectors.toList());
        }
        projectExecution.setProjectExecutionSteps(projectExecutionSteps);
        // 推荐供应商
        for (ProjectSup projectSup : project.getProjectSups()) {
            ProjectExecutionSup projectExecutionSup = createProjectExecutionSup(projectSup);
            projectExecution.getProjectExecutionSups().add(projectExecutionSup);
        }

        // 附件
        for (ProjectAtt projectAtt : project.getProjectAtts()) {
            ProjectExecutionAtt projectExecutionAtt = createProjectAtt(projectAtt);
            projectExecution.getProjectExecutionAtts().add(projectExecutionAtt);
        }

        try {
            return projectExecutionRepository.save(projectExecution).getId();
        } catch (Exception e) {
            log.error("生成采购方案执行异常", e);
            throw businessException("生成采购方案执行失败");
        }
    }

    /**
     * 生成专家抽取
     * @param project
     */
    private void generateDrawExpert(Project project) {
        DrawExpert drawExpert = new DrawExpert();
        drawExpert.setReceipt(UUID.randomUUID().toString());
        drawExpert.setEvent(project.getName() + "专家抽取");
        drawExpert.setSort("评审会");
        Integer sum = project.getProjectDrawExpertRules().stream()
                .map(BaseDrawExpertRule::getExpertCount)
                .reduce(Integer::sum).orElse(0);
        drawExpert.setMeetingNumber(sum);
        drawExpert.setDrawNumber(sum * 3);
        drawExpert.setDrawer(project.getCreatedUserId());
        drawExpert.setTenantId(project.getTenantId());
        drawExpert.setDepartmentId(project.getDepartmentId());
        drawExpert.setDrawTime(LocalDateTime.now());
        drawExpert.setPhone("");
        drawExpert.setDescription(project.getExpertDescription());
        drawExpert.setNoticeExpert("");
        drawExpert.setValidStatus(true);
        // TODO: 2021/2/2 抽取规则子表
        try {
            drawExpertRepository.saveAndFlush(drawExpert);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("生成专家抽取异常", e);
            }
            throw businessException("生成采购方案失败");
        }
    }

    /**
     * 生成评标会
     * @param project
     */
    private void generateMeeting(Project project,Integer projExecId) {
        Meeting meeting = new Meeting();
        meeting.setTenantId(project.getTenantId());
        meeting.setDepartmentId(project.getDepartmentId());
        meeting.setName(project.getName());
        meeting.setMeetingHostUserId(project.getCreatedUserId());
        meeting.setProjId(project.getId());
        meeting.setGraded(false);
        meeting.setFinishGrade(false);

        ProjectExecution projectExecution = getProjectExecution(projExecId);
//        DrawExpert drawExpert = getDrawExpert(project.getId());
//        List<DrawExpertList> drawExpertLists = drawExpert.getDrawExpertLists();
//        List<MeetingConferee> meetingConferees = new ArrayList<>();
//
//
//        for (DrawExpertList expertList : drawExpertLists) {
//            MeetingConferee meetingConferee = new MeetingConferee();
//            meetingConferee.setType(expertList.getExpertType());
//            meetingConferee.setMeeting(meeting);
//            meetingConferee.setSignStatus(MeetingConferee.SignStatus.WBJ);
//            meetingConferee.setUserId(expertList.getExpert().getMemberCode());
//            meetingConferees.add(meetingConferee);
//        }
//
//        meeting.setConferees(meetingConferees);
        meeting.setProjectExecution(projectExecution.getId());
        try {
            meetingRepository.saveAndFlush(meeting);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("生成会议异常", e);
            }
            throw businessException("生成评审会失败");
        }
    }

    /**
     * 保存历史记录
     * @param project
     */
    private void saveHistory(Project project) {
        try {
            HistoricProject historicProject = historicProjectDetailsMapper.fromProject(project);
            historicProject.setProject(project);
            historicProjectRepository.saveAndFlush(historicProject);
        } catch (Exception e) {
            throw businessException("保存到历史表失败");
        }
    }

    private ProjectExecutionAtt createProjectAtt(ProjectAtt projectAtt) {
        ProjectExecutionAtt projectExecutionAtt = new ProjectExecutionAtt();
        projectExecutionAtt.setProjAttId(projectAtt.getId());
        projectExecutionAtt.setName(projectAtt.getName());
        projectExecutionAtt.setSource(projectAtt.getSource());
        projectExecutionAtt.setSize(projectAtt.getSize());
        projectExecutionAtt.setExt(projectAtt.getExt());
        projectExecutionAtt.setUploadTime(projectAtt.getUploadTime());
        projectExecutionAtt.setPath(projectAtt.getPath());
        return projectExecutionAtt;
    }

    private ProjectExecutionSup createProjectExecutionSup(ProjectSup projectSup) {
        ProjectExecutionSup projectExecutionSup = new ProjectExecutionSup();
        projectExecutionSup.setProjSupId(projectSup.getId());
        projectExecutionSup.setSource(projectSup.getSource());
        updateProjectExecutionSup(projectExecutionSup, projectSup);
        return projectExecutionSup;
    }

    private void updateProjectExecutionSup(ProjectExecutionSup projectExecutionSup, ProjectSup projectSup) {
        projectExecutionSup.setProjSupId(projectSup.getId());
        projectExecutionSup.setSupplierId(projectSup.getSupplierId());
        projectExecutionSup.setContactName(projectSup.getContactName());
        projectExecutionSup.setContactMobile(projectSup.getContactMobile());
        projectExecutionSup.setContactEmail(projectSup.getContactEmail());
        projectExecutionSup.setReason(projectSup.getReason());
        projectExecutionSup.setNote(projectSup.getNote());
        projectExecutionSup.setModifyMode(ModifyMode.W);
    }

    /**
     * 根据ID获取专家抽取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected DrawExpert getDrawExpert(Integer id) {
        Objects.requireNonNull(id);
        List<DrawExpert> drawExpert1 = new ArrayList<>();
        Sort sort = Sort.by(Sort.Direction.DESC, "createdTime");
        Page<DrawExpert> all = drawExpertRepository.findAll((
                ((Specification<DrawExpert>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("projectId"), id))
                        .and(jpaQueryHelper.dataRuleSpecification())), PageRequest.of(0, 1, sort));
        if (all.isEmpty()) {
            if (!drawExpertRepository.existsById(id)) {
                System.out.println("找不到相应专家抽取");
            }
            throw dataAccessForbidden();
        }
        drawExpert1 = all.getContent();
        return drawExpert1.get(0);
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

}
