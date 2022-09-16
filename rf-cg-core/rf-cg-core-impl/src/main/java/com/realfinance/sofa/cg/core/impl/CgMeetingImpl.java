package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.DrawExpert;
import com.realfinance.sofa.cg.core.domain.DrawExpertList;
import com.realfinance.sofa.cg.core.domain.ExpertType;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionAtt;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecutionSup;
import com.realfinance.sofa.cg.core.domain.meeting.*;
import com.realfinance.sofa.cg.core.facade.CgMeetingFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.*;
import com.realfinance.sofa.cg.core.service.mapstruct.*;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgMeetingFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgMeetingImpl implements CgMeetingFacade {

    private static final Logger log = LoggerFactory.getLogger(CgMeetingImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final MeetingRepository meetingRepository;
    private final MeetingConfereeRepository meetingConfereeRepository;
    private final MeetingSupplierRepository meetingSupplierRepository;
    private final MeetingFileRepository meetingFileRepository;
    private final DrawExpertRepository drawExpertRepository;
    private final MeetingMapper meetingMapper;
    private final AuditQualificationMapper auditQualificationMapper;
    private final AuditResponseMapper auditResponseMapper;
    private final GradeSupSumMapper gradeSupSumMapper;
    private final GradeSupMapper gradeSupMapper;
    private final MeetingConfereeMapper meetingConfereeMapper;
    private final MeetingSupplierMapper meetingSupplierMapper;
    private final MeetingFileMapper meetingFileMapper;
    private final MeetingDetailsMapper meetingDetailsMapper;
    private final MeetingSaveMapper meetingSaveMapper;
    private final AuditQualRepository auditQualRepository;
    private final GradeSupSumRepository gradeSupSumRepository;
    private final GradeSupRepository gradeSupRepository;
    private final AuditRespRepository auditRespRepository;
    private final MeetingConfereeSaveMapper meetingConfereeSaveMapper;
    private final ProjectExecutionRepository projectExecutionRepository;

    public CgMeetingImpl(JpaQueryHelper jpaQueryHelper, MeetingRepository meetingRepository, MeetingConfereeRepository meetingConfereeRepository, MeetingSupplierRepository meetingSupplierRepository, MeetingFileRepository meetingFileRepository, DrawExpertRepository drawExpertRepository, MeetingMapper meetingMapper, AuditQualificationMapper auditQualificationMapper, AuditResponseMapper auditResponseMapper, GradeSupSumMapper gradeSupSumMapper, GradeSupMapper gradeSupMapper, MeetingConfereeMapper meetingConfereeMapper, MeetingSupplierMapper meetingSupplierMapper, MeetingFileMapper meetingFileMapper, MeetingDetailsMapper meetingDetailsMapper, MeetingSaveMapper meetingSaveMapper, AuditQualRepository auditQualRepository, GradeSupSumRepository gradeSupSumRepository, GradeSupRepository gradeSupRepository, AuditRespRepository auditRespRepository, MeetingConfereeSaveMapper meetingConfereeSaveMapper, ProjectExecutionRepository projectExecutionRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.meetingRepository = meetingRepository;
        this.meetingConfereeRepository = meetingConfereeRepository;
        this.meetingSupplierRepository = meetingSupplierRepository;
        this.meetingFileRepository = meetingFileRepository;
        this.drawExpertRepository = drawExpertRepository;
        this.meetingMapper = meetingMapper;
        this.auditQualificationMapper = auditQualificationMapper;
        this.auditResponseMapper = auditResponseMapper;
        this.gradeSupSumMapper = gradeSupSumMapper;
        this.gradeSupMapper = gradeSupMapper;
        this.meetingConfereeMapper = meetingConfereeMapper;
        this.meetingSupplierMapper = meetingSupplierMapper;
        this.meetingFileMapper = meetingFileMapper;
        this.meetingDetailsMapper = meetingDetailsMapper;
        this.meetingSaveMapper = meetingSaveMapper;
        this.auditQualRepository = auditQualRepository;
        this.gradeSupSumRepository = gradeSupSumRepository;
        this.gradeSupRepository = gradeSupRepository;
        this.auditRespRepository = auditRespRepository;
        this.meetingConfereeSaveMapper = meetingConfereeSaveMapper;
        this.projectExecutionRepository = projectExecutionRepository;
    }

    @Override
    public Page<CgMeetingDto> list(CgMeetingQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<Meeting> result = meetingRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(meetingMapper::toDto);
    }

    @Override
    public Page<CgMeetingConfereeDto> confereeList(CgConfereeQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<MeetingConferee> result = meetingConfereeRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(meetingConfereeMapper::toDto);
    }

    @Override
    public List<CgMeetingConfereeDto> confereeListExpert(CgConfereeQueryCriteria queryCriteria) {
        List<MeetingConferee> result = meetingConfereeRepository.findAll(QueryCriteriaUtils.toSpecificationExpert(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));
        return result.stream().map(meetingConfereeMapper::toDto).collect(Collectors.toList());
    }

    /**
     * 会议与专家关联
     * @param id 抽取专家Id
     * @param cgDrawExpertListDtos 抽取专家列表
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveMeeting(Integer id, List<CgDrawExpertListDto> cgDrawExpertListDtos) {
        Meeting saveDto = getMeetingByProjId(id);
        List<MeetingConferee> meetingConferees = new ArrayList<>();
        for (CgDrawExpertListDto expertList : cgDrawExpertListDtos) {
            MeetingConferee meetingConferee = new MeetingConferee();
            meetingConferee.setType(Enum.valueOf(ExpertType.class, expertList.getExpertType()));
            meetingConferee.setMeeting(saveDto);
            meetingConferee.setSignStatus(MeetingConferee.SignStatus.WBJ);
            meetingConferee.setUserId(expertList.getExpert().getMemberCode());
            meetingConferees.add(meetingConferee);
        }
        saveDto.setConferees(meetingConferees);
        try {
            Meeting saved = meetingRepository.saveAndFlush(saveDto);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    /**
     * 更新决议内容
     * @param id 会议Id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateResoCont(Integer id,String content) {
        try {
            Integer saved = meetingRepository.setContentById(content,id);
            return saved;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    /**
     * 更行是否开启评分
     * @param id 会议Id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateIsGraded(Boolean isGraded, Integer id) {
        try {
            return meetingRepository.setIsGradedById(isGraded, id);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional
    public Integer updateFinishGrade(Boolean finishGrade, Integer id) {
        try {
            return meetingRepository.setFinishGradeById(finishGrade, id);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    /**
     * 专家签到
     * @param id 参会人员Id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateConfereeSignIn(Integer id) {
        try {
            return meetingConfereeRepository.setSignInTimeById(LocalDateTime.now(),id);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    public Page<CgProjectExecutionSupDto> listSupplier(CgSupplierQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<ProjectExecutionSup> result = meetingSupplierRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(meetingSupplierMapper::toDto);
    }

    @Override
    public List<CgProjectExecutionSupDto> listSupplier(CgSupplierQueryCriteria queryCriteria) {
        List<ProjectExecutionSup> result = meetingSupplierRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));
        return result.stream().map(meetingSupplierMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<CgProjectExecutionAttDto> listFile(CgAttaFileQueryCriteria queryCriteria) {
        List<ProjectExecutionAtt> result = meetingFileRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));
        return result.stream().map(meetingFileMapper::toDto).collect(Collectors.toList());
    }

    /**
     * 开启会议
     * @param id 开启会议
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer startMeeting(@NotNull Integer id) {
        Meeting meeting = getMeeting(id);
        if(!drawExpertRepository.existsByProjectId(meeting.getProjId())){
            throw businessException("未抽取专家，开启会议前请先抽取专家");
        }
        DrawExpert drawExpert = getDrawExpert(meeting.getProjId());
        if(drawExpert.getConfirmNumber()==0){
            throw businessException("目前确认参会专家为0人，请抽取专家并确保抽取的专家已确认参会");
        }
        if(drawExpert.getConfirmNumber()<drawExpert.getMeetingNumber()){
            throw businessException("确认参会专家不足，请提示专家确认参会");
        }
        if(drawExpert.getEndTime()!=null){
            throw businessException("二次评审需要重新抽取专家");
        }
        List<DrawExpertList> drawExpertLists = drawExpert.getDrawExpertLists();
        for (DrawExpertList expertList : drawExpertLists) {
            if(expertList.getIsAttend()==1){
                MeetingConferee meetingConferee = new MeetingConferee();
                meetingConferee.setType(expertList.getExpertType());
                meetingConferee.setMeeting(meeting);
                meetingConferee.setSignStatus(MeetingConferee.SignStatus.WBJ);
                meetingConferee.setUserId(expertList.getExpert().getMemberCode());
                meetingConferee.setOnline(false);
                meetingConferee.setProjectId(meeting.getProjId());
                meetingConferee.setExpertId(expertList.getExpert().getId());
                meetingConferee.setDrawWay(expertList.getDrawWay());
                try {
                    MeetingConferee saved = meetingConfereeRepository.saveAndFlush(meetingConferee);
                    System.out.println(saved.getId());
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("保存失败",e);
                    }
                    throw businessException("保存失败");
                }
            }
        }

        meeting.setStartTime(LocalDateTime.now());
        try {
            Meeting saved = meetingRepository.saveAndFlush(meeting);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败");
        }
    }

    /**
     * 结束会议
     * @param id 会议id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer endMeeting(@NotNull Integer id) {
        Meeting meeting = getMeeting(id);
        DrawExpert drawExpert = getDrawExpert(meeting.getProjId());
        try {
            drawExpertRepository.setEndTimeById(LocalDateTime.now(),drawExpert.getId());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("专家抽取更新结束状态失败",e);
            }
            throw businessException("专家抽取更新结束状态失败");
        }

        try {
            return meetingRepository.setEndTimeById(LocalDateTime.now(),id);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("结束会议失败",e);
            }
            throw businessException("结束会议失败");
        }
    }

    @Override
    public CgMeetingConfereeDto getConfereeByUserIdAndMeetingId(@NotNull Integer userId, @NotNull Integer meetingId) {
        Objects.requireNonNull(userId);
        List<MeetingConferee> all = meetingConfereeRepository.findAll(
                ((Specification<MeetingConferee>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("userId"), userId)).and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("meeting").get("id"),meetingId))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            System.out.println("未找到相应的参会人员");
            throw dataAccessForbidden();
        }
        return all.stream().map(meetingConfereeMapper::toDto).collect(Collectors.toList()).get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveMeetingConferee(@NotNull CgMeetingConfereeDto saveDto) {
        Objects.requireNonNull(saveDto);
        MeetingConferee meetingConferee = new MeetingConferee();
        System.out.println(saveDto.getId());
        if (saveDto.getId() == null) {
            System.out.println("跳过此处操作");
        } else { // 修改
            MeetingConferee entity = getMeetingConferee(saveDto.getId());
            meetingConferee = meetingConfereeSaveMapper.updateEntity(entity,saveDto);
        }
        try {
            MeetingConferee saved = meetingConfereeRepository.saveAndFlush(meetingConferee);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateMeetingConferee(@NotNull Integer id,Integer content) {
        try {
            return meetingConfereeRepository.setContentById(content,id);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("参会人员更新失败",e);
            }
            throw businessException("参会人员更新失败,because:"+e.getMessage());
        }
    }

    @Override
    public CgMeetingDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
        CgMeetingDetailsDto result = meetingDetailsMapper.toDto(getMeeting(id));
        return result;
    }

    @Override
    public CgMeetingDetailsDto getDetailsByProjectId(@NotNull Integer id) {
        Objects.requireNonNull(id);
        Meeting meeting = getMeetingByProjId(id);
        CgMeetingDetailsDto result = meetingDetailsMapper.toDto(meeting);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer openQuote(@NotNull CgMeetingDetailsDto saveDto) {
        Objects.requireNonNull(saveDto.getId());
//        Meeting meeting;
//        Boolean fileTecBusiness = null;
//        System.out.println(saveDto.getId());
//        Meeting entity = getMeeting(saveDto.getId());
//        if(entity.getFileTecBusiness() != null){
//            fileTecBusiness = entity.getFileTecBusiness();
//        }
//        meeting = meetingSaveMapper.updateEntity(entity,saveDto);
//
//        if(meeting.getFilePrice() == null)
//            meeting.setFilePrice(true);
//        meeting.setFileTecBusiness(fileTecBusiness);
        try {
            return meetingRepository.setFilePriceById(true,saveDto.getId());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }


    public List<CgAuditQualificationDto> listQualexam(CgAuditQualQueryCriteria queryCriteria) {
        List<AuditQualification> auditQualifications = auditQualRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));

        return auditQualifications.stream().map(auditQualificationMapper::toDto).collect(Collectors.toList());
    }

    public List<CgAuditResponseDto> listRespexam(CgAuditRespQueryCriteria queryCriteria) {
        List<AuditResponse> auditResponses = auditRespRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));

        return auditResponses.stream().map(auditResponseMapper::toDto).collect(Collectors.toList());
    }

    public List<CgGradeSupSumDto> listGradeSupSum(CgGradeSupSumQueryCriteria queryCriteria,Sort sort) {
        List<GradeSupSum> gradeSupSums = gradeSupSumRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()),sort);
        return gradeSupSums.stream().map(gradeSupSumMapper::toDto).collect(Collectors.toList());
    }

    public List<CgGradeSupSumDetailsDto> listGradeSupSumDetails(CgGradeSupSumQueryCriteria queryCriteria) {
        List<GradeSupSum> gradeSupSums = gradeSupSumRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));
        return gradeSupSums.stream().map(gradeSupSumMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<CgGradeSupSumDetailsDto> listGradeSupSumDetails(CgGradeSupSumQueryCriteria queryCriteria, Sort sort) {
        List<GradeSupSum> gradeSupSums = gradeSupSumRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()),sort);
        return gradeSupSums.stream().map(gradeSupSumMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CgGradeSupSumDetailsDto getGradeDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        CgGradeSupSumDetailsDto result = gradeSupSumMapper.toDto(getGradeSupSum(id));
        return result;
    }


    public List<CgGradeSupDto> listGradeSup(CgGradeSupQueryCriteria queryCriteria) {
        List<GradeSup> gradeSups = gradeSupRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));
        return gradeSups.stream().map(gradeSupMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer openBiz(@NotNull CgMeetingDetailsDto saveDto) {
        Objects.requireNonNull(saveDto.getId());
//        Meeting meeting;
//        Boolean filePrice = null;
//        System.out.println(saveDto.getId());
//        Meeting entity = getMeeting(saveDto.getId());
//        if(entity.getFilePrice() != null){
//            filePrice = entity.getFilePrice();
//        }
//        meeting = meetingSaveMapper.updateEntity(entity,saveDto);
//
//        if(meeting.getFileTecBusiness()==null)
//            meeting.setFileTecBusiness(true);
//        meeting.setFilePrice(filePrice);
        try {
            return meetingRepository.setFileTecBusinessById(true,saveDto.getId());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveQualExamFirst(List<CgProjectExecutionSupDto> suppliers, List<CgBiddingDocumentExaminationDto> biddingDocQualExams,List<CgMeetingConfereeDto> confereeOfExperts,Integer biddingDocumentId) {
        for (CgBiddingDocumentExaminationDto biddingDocQualExam : biddingDocQualExams) {
            for (CgProjectExecutionSupDto supplier : suppliers) {
                for (CgMeetingConfereeDto confereeOfExpert : confereeOfExperts) {
                    AuditQualification auditQualification = new AuditQualification();
                    auditQualification.setCode(biddingDocQualExam.getCode());
                    auditQualification.setName(biddingDocQualExam.getName());
                    auditQualification.setSubCode(biddingDocQualExam.getSubCode());
                    auditQualification.setSubName(biddingDocQualExam.getSubName());
                    auditQualification.setSupplier(supplier.getSupplierId());
                    auditQualification.setExpert(confereeOfExpert.getUserId());
                    auditQualification.setBiddingDocumentId(biddingDocumentId);
                    auditQualification.setPass(true);
                    try {
                        AuditQualification saved = auditQualRepository.saveAndFlush(auditQualification);
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("保存失败",e);
                        }
                        throw businessException("保存失败,because:"+e.getMessage());
                    }
                }
            }
        }
        return 200;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveRespExamFirst(List<CgProjectExecutionSupDto> suppliers, List<CgBiddingDocumentExaminationDto> biddingDocQualExams, List<CgMeetingConfereeDto> confereeOfExperts,Integer biddingDocumentId) {
        for (CgBiddingDocumentExaminationDto biddingDocQualExam : biddingDocQualExams) {
            for (CgProjectExecutionSupDto supplier : suppliers) {
                for (CgMeetingConfereeDto confereeOfExpert : confereeOfExperts) {
                    AuditResponse auditResponse = new AuditResponse();
                    auditResponse.setCode(biddingDocQualExam.getCode());
                    auditResponse.setName(biddingDocQualExam.getName());
                    auditResponse.setSubCode(biddingDocQualExam.getSubCode());
                    auditResponse.setSubName(biddingDocQualExam.getSubName());
                    auditResponse.setSupplier(supplier.getSupplierId());
                    auditResponse.setExpert(confereeOfExpert.getUserId());
                    auditResponse.setBiddingDocumentId(biddingDocumentId);
                    auditResponse.setPass(true);
                    try {
                        AuditResponse saved = auditRespRepository.saveAndFlush(auditResponse);
                        System.out.println("生成响应性审查");
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("保存失败",e);
                        }
                        throw businessException("保存失败,because:"+e.getMessage());
                    }
                }
            }
        }
        return 200;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveGradeSupSumFirst(List<CgProjectExecutionSupDto> suppliers, List<CgProjectEvalDto> projectEvals, List<CgMeetingConfereeDto> confereeOfExperts, Integer projId,Integer meetingId) {
        List<CgProjectEvalRuleDto> projectEvalRules = new ArrayList<>();
        for (CgProjectEvalDto projectEval : projectEvals) {
            projectEvalRules.addAll(projectEval.getProjectEvalRules());
        }
        List<CgProjectEvalRuleDto> cgProjectEvalDtos = new ArrayList(projectEvalRules);

        for (CgProjectExecutionSupDto supplier : suppliers) {
            GradeSupSum gradeSupSum = new GradeSupSum();
            List<GradeSup> gradeSups = new ArrayList<>();
            List<GradeSup> gradeSups2 = new ArrayList<>();
            for (int i = 0; i < projectEvalRules.size(); i++) {
                for (CgMeetingConfereeDto confereeOfExpert : confereeOfExperts) {
                    gradeSupSum.setProjId(projId);
                    gradeSupSum.setMeetingId(meetingId);
                    gradeSupSum.setSupplier(supplier.getSupplierId());
                    GradeSup gradeSup = new GradeSup();
                    CgProjectEvalRuleDto cgProjectEvalDto = cgProjectEvalDtos.get(i);
                    gradeSup.setName(cgProjectEvalDto.getName());
                    gradeSup.setSubCode(projectEvalRules.get(i).getSubCode());//原本直接赋值“i”
                    gradeSup.setSubName(cgProjectEvalDto.getSubName());
                    gradeSup.setSupplier(supplier.getSupplierId());
                    gradeSup.setExpert(confereeOfExpert.getUserId());//原本为getUserId
                    gradeSup.setNote(cgProjectEvalDto.getNote());
                    gradeSup.setWeight(cgProjectEvalDto.getWeight());
                    gradeSup.setProjEvalRuleId(cgProjectEvalDto.getId());
                    gradeSup.setProjId(projId);
                    gradeSups2.add(gradeSup);
                }
            }
            gradeSups = gradeSups2;
            gradeSupSum.setGradeSups(gradeSups);
            try {
                GradeSupSum saved = gradeSupSumRepository.saveAndFlush(gradeSupSum);
                System.out.println("生成响应性审查");
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败", e);
                }
                throw businessException("保存失败,because:" + e.getMessage());
            }
        }
        return 200;
    }

    @Override
    @Transactional
    public Integer saveVoteSumFirst(List<CgProjectExecutionSupDto> suppliers, List<CgMeetingConfereeDto> confereeOfExperts, Integer projId, Integer meetingId) {
//        List<CgProjectEvalRuleDto> projectEvalRules = new ArrayList<>();
//        for (CgProjectEvalDto projectEval : projectEvals) {
//            projectEvalRules.addAll(projectEval.getProjectEvalRules());
//        }
//        List<CgProjectEvalRuleDto> cgProjectEvalDtos = new ArrayList(projectEvalRules);

        for (CgProjectExecutionSupDto supplier : suppliers) {
            GradeSupSum gradeSupSum = new GradeSupSum();
            List<GradeSup> gradeSups = new ArrayList<>();
            List<GradeSup> gradeSups2 = new ArrayList<>();
//            for (int i = 0; i < projectEvalRules.size(); i++) {
                for (CgMeetingConfereeDto confereeOfExpert : confereeOfExperts) {
                    gradeSupSum.setProjId(projId);
                    gradeSupSum.setMeetingId(meetingId);
                    gradeSupSum.setSupplier(supplier.getSupplierId());
                    GradeSup gradeSup = new GradeSup();
//                    CgProjectEvalRuleDto cgProjectEvalDto = cgProjectEvalDtos.get(i);
//                    gradeSup.setName(cgProjectEvalDto.getName());
//                    gradeSup.setSubCode(i + "");
//                    gradeSup.setSubName(cgProjectEvalDto.getSubName());
                    gradeSup.setSupplier(supplier.getSupplierId());
                    gradeSup.setExpert(confereeOfExpert.getUserId());//原本为getUserId
//                    gradeSup.setNote(cgProjectEvalDto.getNote());
//                    gradeSup.setWeight(cgProjectEvalDto.getWeight());
//                    gradeSup.setProjEvalRuleId(cgProjectEvalDto.getId());
                    gradeSup.setProjId(projId);
                    gradeSups2.add(gradeSup);
                }
//            }
            gradeSups = gradeSups2;
            gradeSupSum.setGradeSups(gradeSups);
            try {
                GradeSupSum saved = gradeSupSumRepository.saveAndFlush(gradeSupSum);
                System.out.println("生成响应性审查");
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败", e);
                }
                throw businessException("保存失败,because:" + e.getMessage());
            }
        }
        return 200;
    }

    @Override
    @Transactional
    public Integer saveVote(List<CgGradeSupDto> gradeSupDtos) {
        for (CgGradeSupDto gradeSupDto : gradeSupDtos) {
            Integer score = gradeSupDto.getScore();
            Integer id = gradeSupDto.getId();
            try {
                gradeSupRepository.setScoreById(score,id);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("保存失败",e);
                }
                throw businessException("保存失败,because:"+e.getMessage());
            }
        }
        return 200;
    }

    @Override
    @Transactional
    public Integer meetingTwice(CgMeetingDetailsDto saveDto) {
//        Meeting one = meetingRepository.getOne(saveDto.getId());
        Meeting meeting = new Meeting();
        meeting.setTenantId(DataScopeUtils.loadTenantId());
        meeting.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
        meeting.setName(saveDto.getName());
        meeting.setMeetingHostUserId(saveDto.getMeetingHostUserId());
        meeting.setProjId(saveDto.getProjId());
        meeting.setGraded(false);
        ProjectExecution projectExecution = getProjectExecution(saveDto.getProjectExecution());
        meeting.setProjectExecution(projectExecution.getId());
        try {
            return meetingRepository.saveAndFlush(meeting).getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("生成二次评审异常", e);
            }
            throw businessException("生成二次评审失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveQualExam(@NotNull CgAuditQualificationDto saveDto) {
        Objects.requireNonNull(saveDto);
        AuditQualification auditQualification = new AuditQualification();
        if (saveDto.getId() == null) { // 新增
            throw businessException("未找到相应的资格性审查");
        } else { // 修改
            AuditQualification entity = getAuditQual(saveDto.getId());
            auditQualification.setId(saveDto.getId());
            auditQualification.setPass(saveDto.getPass());
            auditQualification.setExpert(entity.getExpert());
            auditQualification.setSupplier(entity.getSupplier());
            auditQualification.setSubName(entity.getSubName());
            auditQualification.setSubCode(entity.getSubCode());
            auditQualification.setCode(entity.getCode());
            auditQualification.setName(entity.getName());
            auditQualification.setBiddingDocumentId(entity.getBiddingDocumentId());
        }
        try {
            AuditQualification saved = auditQualRepository.saveAndFlush(auditQualification);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveRespExam(@NotNull CgAuditResponseDto saveDto) {
        Objects.requireNonNull(saveDto);
        AuditResponse auditResponse = new AuditResponse();
        if (saveDto.getId() == null) { // 新增
            throw businessException("未找到相应的响应性审查");
        } else { // 修改
            AuditResponse entity = getAuditResp(saveDto.getId());
            auditResponse.setId(saveDto.getId());
            auditResponse.setPass(saveDto.getPass());
            auditResponse.setExpert(entity.getExpert());
            auditResponse.setSupplier(entity.getSupplier());
            auditResponse.setSubName(entity.getSubName());
            auditResponse.setSubCode(entity.getSubCode());
            auditResponse.setCode(entity.getCode());
            auditResponse.setName(entity.getName());
            auditResponse.setBiddingDocumentId(entity.getBiddingDocumentId());
        }
        try {
            AuditResponse saved = auditRespRepository.saveAndFlush(auditResponse);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }


    /**
     * 更行是否开启评分
     * @param id 会议Id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGradeSup(Integer score, Integer id) {
        try {
            gradeSupRepository.setScoreById(score,id);
            System.out.println(id);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGradeSupSum(Double sumScore, Integer id) {
        try {
            gradeSupSumRepository.setSumScoreById(sumScore,id);
            System.out.println(id);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGradeSupSumRanking(Integer ranking, Integer id) {
        try {
            gradeSupSumRepository.setSumRankingById(ranking,id);
            System.out.println(id);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败,because:"+e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveGradeSupSumIsSum(Boolean IsSum, Integer id) {
        try {
            gradeSupSumRepository.setIsSumById(IsSum, id);
            System.out.println(id);
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
    protected Meeting getMeeting(Integer id) {
        Objects.requireNonNull(id);
        List<Meeting> all = meetingRepository.findAll(
                ((Specification<Meeting>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!meetingRepository.existsById(id)) {
                throw entityNotFound(Meeting.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected GradeSupSum getGradeSupSum(Integer id) {
        Objects.requireNonNull(id);
        List<GradeSupSum> all = gradeSupSumRepository.findAll(
                ((Specification<GradeSupSum>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!meetingRepository.existsById(id)) {
                throw entityNotFound(Meeting.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected Meeting getMeetingByProjId(Integer id) {
        Objects.requireNonNull(id);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdTime");
        Page<Meeting> all = meetingRepository.findAll((
                ((Specification<Meeting>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("projId"), id))
                        .and(jpaQueryHelper.dataRuleSpecification())),PageRequest.of(0,1,sort));
        if (all.isEmpty()) {
            if (!meetingRepository.existsById(id)) {
                throw entityNotFound(Meeting.class,"id",id);
            }
            throw dataAccessForbidden();
        }
        return all.getContent().get(0);
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
                        .and(jpaQueryHelper.dataRuleSpecification())),PageRequest.of(0, 1, sort));
        if (all.isEmpty()) {
            if (!drawExpertRepository.existsById(id)) {
                System.out.println("找不到相应专家抽取");
            }
            throw dataAccessForbidden();
        }
        drawExpert1 = all.getContent();
        return drawExpert1.get(0);
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected AuditQualification getAuditQual(Integer id) {
        Objects.requireNonNull(id);
        List<AuditQualification> all = auditQualRepository.findAll(
                ((Specification<AuditQualification>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!auditQualRepository.existsById(id)) {
                System.out.println("找不到资格性审查");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected AuditResponse getAuditResp(Integer id) {
        Objects.requireNonNull(id);
        List<AuditResponse> all = auditRespRepository.findAll(
                ((Specification<AuditResponse>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!auditRespRepository.existsById(id)) {
                System.out.println("找不到响应性审查");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
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

    /**
     * 根据ID获取实体
     * 校验数据权限
     * @param id
     * @return
     */
    protected MeetingConferee getMeetingConferee(Integer id) {
        Objects.requireNonNull(id);
        List<MeetingConferee> all = meetingConfereeRepository.findAll(
                ((Specification<MeetingConferee>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!meetingConfereeRepository.existsById(id)) {
                System.out.println("找不到相应的参会人员");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

}
