package com.realfinance.sofa.cg.controller.cg.core;

import com.alibaba.fastjson.JSON;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.realfinance.sofa.cg.core.facade.*;
import com.realfinance.sofa.cg.core.model.CgSupplierQueryCriteria;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.security.AuthInfo;
import com.realfinance.sofa.cg.security.User;
import com.realfinance.sofa.cg.service.mapstruct.*;
import com.realfinance.sofa.cg.sup.facade.CgBusinessReplyFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.util.DataRuleHelper;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.cg.util.RpcUtils;
import com.realfinance.sofa.common.filestore.FileCipherUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Tag(name = "采购评审会")
@RequestMapping("/cg/core/meeting")
public class MeetingController {
    private static final Logger log = LoggerFactory.getLogger(MeetingController.class);

    public static final String MENU_CODE_ROOT = "purprojmtg";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";

    @SofaReference(interfaceType = CgMeetingFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgMeetingFacade cgMeetingFacade;
    @SofaReference(interfaceType = CgChatRecordFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgChatRecordFacade cgChatRecordFacade;
    @SofaReference(interfaceType = CgProjectExecutionFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectExecutionFacade cgProjectExecutionFacade;
    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgProjectFacade cgProjectFacade;
    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;
    @SofaReference(interfaceType = CgBiddingDocumentFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBiddingDocumentFacade cgBiddingDocumentFacade;
    @SofaReference(interfaceType = CgDrawExpertFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgDrawExpertFacade cgDrawExpertFacade;
    @SofaReference(interfaceType = CgBusinessReplyFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgBusinessReplyFacade cgBusinessReplyFacade;

    @Resource
    private DataRuleHelper dataRuleHelper;
    @Resource
    private CgMeetingMapper cgMeetingMapper;
    @Resource
    private CgMeetingConfereeMapper cgMeetingConfereeMapper;
    @Resource
    private CgMeetingSupplierMapper cgMeetingSupplierMapper;
    @Resource
    private CgMeetingFileMapper cgMeetingFileMapper;
    @Resource
    private CgChatRecordMapper cgChatRecordMapper;
    @Resource
    private CgProjectMapper cgProjectMapper;
    @Resource
    private CgSupplierMapper cgSupplierMapper;
    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;
    @Resource
    private SubProtocolWebSocketHandler subProtocolWebSocketHandler;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private CgProjectExecutionMapper cgProjectExecutionMapper;
    @Resource
    private CgGradeSupSumMapper cgGradeSupSumMapper;
    @Resource
    private CgGradeSupMapper cgGradeSupMapper;
    @Resource
    private CgBiddingDocumentMapper cgBiddingDocumentMapper;
    @Resource
    private FileStore fileStore;

    /**
     * 查询
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @ResponseBody
    @GetMapping("list")
    @Operation(summary = "查询评审会列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgMeetingVo>> list(@ParameterObject CgMeetingQueryCriteria queryCriteria,
                                                  Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgMeetingDto> result = cgMeetingFacade.list(queryCriteria, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("modifiedTime").descending()));
        return ResponseEntity.ok(result.map(cgMeetingMapper::toVo));
    }

    @ResponseBody
    @GetMapping("getdetailsbyid")
    @Operation(summary = "查询评审会详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<CgMeetingVo> get(@RequestParam Integer id,
                                           @AuthenticationPrincipal AuthInfo authInfo) {
        CgMeetingDetailsDto result = cgMeetingFacade.getDetailsById(id);
        if (result.getStartTime() == null && !Objects.equals(result.getMeetingHostUserId(),authInfo.getUser().getId())) {
            throw new RuntimeException("会议未开始");
        }
        Integer projectExecution = result.getProjectExecution();
        CgProjectExecutionDetailsDto executionDetailsDto = cgProjectExecutionFacade.getDetailsById(projectExecution);
        CgProjectExecutionVo executionVo = cgProjectExecutionMapper.toVo(executionDetailsDto);
        CgMeetingVo meetingVo = cgMeetingMapper.toVo(result);
        setReplyInfo(executionVo,meetingVo);
        return ResponseEntity.ok(meetingVo);
    }

    /**
     * 查询
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @ResponseBody
    @GetMapping("listconferee")
    @Operation(summary = "查询參加专家列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgMeetingConfereeVo>> listConferee(@ParameterObject CgConfereeQueryCriteria queryCriteria,
                                                          Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgMeetingConfereeDto> result = cgMeetingFacade.confereeList(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgMeetingConfereeMapper::toVo));
    }

    /**
     * 查询
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @ResponseBody
    @GetMapping("getCalibrationById")
    @Operation(summary = "查询评标定标报告")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<CgCalibrationVo> getCalibrationById(@ParameterObject CgConfereeQueryCriteria queryCriteria,
                                                                  Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgCalibrationVo cgCalibrationVo = new CgCalibrationVo();
        CgProjectDetailsDto projectDetailsDto = cgProjectFacade.getDetailsById(queryCriteria.getProjectId());
        CgMeetingDetailsDto meetingDetailsDto = cgMeetingFacade.getDetailsByProjectId(queryCriteria.getProjectId());
        queryCriteria.setMeetingId(meetingDetailsDto.getId());
        queryCriteria.setType("PBZJ");
        List<CgMeetingConfereeDto> expert = cgMeetingFacade.confereeListExpert(queryCriteria);
        queryCriteria.setType("HGBJDR");
        List<CgMeetingConfereeVo> supervisor1 = cgMeetingFacade.confereeListExpert(queryCriteria).stream().map(cgMeetingConfereeMapper::toVo).collect(Collectors.toList());
        queryCriteria.setType("JWJDR");
        List<CgMeetingConfereeVo> supervisor2 = cgMeetingFacade.confereeListExpert(queryCriteria).stream().map(cgMeetingConfereeMapper::toVo).collect(Collectors.toList());
        CgDrawExpertDetailsDto drawExpertDetailsDto = cgDrawExpertFacade.getDetailsByProjectId(queryCriteria.getProjectId());

        cgCalibrationVo.setName(projectDetailsDto.getName());
        cgCalibrationVo.setProjectNo(projectDetailsDto.getProjectNo());
        cgCalibrationVo.setEvalMethod(projectDetailsDto.getEvalMethod());
        cgCalibrationVo.setBiddingTime(drawExpertDetailsDto.getBiddingTime());
        cgCalibrationVo.setNoticeExpert(drawExpertDetailsDto.getNoticeExpert());
        cgCalibrationVo.setResolutionContent(meetingDetailsDto.getResolutionContent());

        //todo:添加监督人逻辑（一个或者多个监督人）
        if(supervisor1.size() != 0){
            cgCalibrationVo.setSupervisor(supervisor1.get(0).getUserId());
        }else if(supervisor2.size() != 0){
            cgCalibrationVo.setSupervisor(supervisor2.get(0).getUserId());
        }

        cgCalibrationVo.setExpertResult(expert.stream().map(cgMeetingConfereeMapper::toVo).collect(Collectors.toList()));
        return ResponseEntity.ok(cgCalibrationVo);
    }

    /**
     * 查询
     * @param queryCriteria
     * @param pageable
     * @return
     */
    @ResponseBody
    @GetMapping("listsupplier")
    @Operation(summary = "查询參加供应商列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<CgProjectExecutionSupVo>> listSupplier(@ParameterObject CgSupplierQueryCriteria queryCriteria,
                                                                  Pageable pageable) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Page<CgProjectExecutionSupDto> result = cgMeetingFacade.listSupplier(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgMeetingSupplierMapper::toVo));
    }

    @ResponseBody
    @GetMapping("getprojdetailsbyid")
    @Operation(summary = "查询评审会中采购方案详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<CgProjectVo> getprojDetailsById(@Parameter(description = "采购方案ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgProjectDetailsDto result = cgProjectFacade.getDetailsById(id);
        CgProjectVo vo = cgProjectMapper.toVo(result);
        return ResponseEntity.ok(vo);
    }

    @ResponseBody
    @GetMapping("getsupdetailsbyid")
    @Operation(summary = "查询评审会中供应商详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<CgSupplierVo> getsupDetailsById(@Parameter(description = "供应商ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgSupplierDetailsDto result = cgSupplierFacade.getDetailsById(id);
        return ResponseEntity.ok(cgSupplierMapper.toVo(result));
    }

    @ResponseBody
    @GetMapping("startmeeting")
    @Operation(summary = "开始会议")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> startMeeting(@Parameter(description = "会议ID") @RequestParam Integer id) {
        Integer savedId = cgMeetingFacade.startMeeting(id);
        return ResponseEntity.ok(savedId);
    }

    @ResponseBody
    @GetMapping("endmeeting")
    @Operation(summary = "结束会议")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> endMeeting(@Parameter(description = "会议ID") @RequestParam Integer id) {
        Integer savedId = cgMeetingFacade.endMeeting(id);
        return ResponseEntity.ok(savedId);
    }

    /**
     * 查询
     * @param queryCriteria
     * @return
     */
    @ResponseBody
    @GetMapping("listfile")
    @Operation(summary = "查询评审会文件")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<List<CgSupplierAttachmentVo>> listFile(@ParameterObject CgBusinessReplyQueryCriteria queryCriteria) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        queryCriteria.setReplyType("招标中");
        List<CgBusinessReplyDetailsDto> listDetails = cgBusinessReplyFacade.listDetails(queryCriteria);
        for (CgBusinessReplyDetailsDto listDetail : listDetails) {
            decryptAtts(listDetail);
        }
        List<CgBusinessReplyVo> replyVoList = listDetails.stream().map(cgBiddingDocumentMapper::toVo).collect(Collectors.toList());
        List<CgSupplierAttachmentVo> attachments = new ArrayList<>();
        for (CgBusinessReplyVo cgBusinessReplyVo : replyVoList) {
            List<CgSupplierAttachmentVo> attachmentVos = cgBusinessReplyVo.getAttUs();
            attachments.addAll(attachmentVos);
        }
        for (CgSupplierAttachmentVo attachment : attachments) {
            FileToken fileToken = FileTokens.create(attachment.getPath(), attachment.getName());
            attachment.setLink(LinkUtils.createFileDownloadLink(fileToken));
        }
        return ResponseEntity.ok(attachments);
    }

    @ResponseBody
    @PostMapping("openquote")
    @Operation(summary = "价格文件专家可看")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> openQuote(Authentication principal, @Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo) {
        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        String username = authInfo.getUser().getUsername();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        String user = realName+"_"+department;
        Integer userId = authInfo.getUser().getId();
        Integer meetingId = vo.getId();
        String message = "系统消息：已开启价格标，请各位点击刷新按钮，在文档列表中可查看，下载相关技术文件";
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(realName+"_"+department);
        cgChatRecordVo.setContent(message);
        cgChatRecordVo.setSendTime(LocalDateTime.now());
        cgChatRecordVo.setMessageType("KQJGB");
        cgChatRecordVo.setProjectExecutionId(vo.getProjectExecution().getId());
        CgMeetingDetailsDto saveDto = cgMeetingMapper.toSaveDto(vo);
        Integer id = cgMeetingFacade.openQuote(saveDto);

        simpMessagingTemplate.convertAndSend("/topic/meeting/" +meetingId,cgChatRecordVo);
        Integer id2 = cgChatRecordFacade.save(user,userId,meetingId,message);
        return ResponseEntity.ok(id);
    }

    @ResponseBody
    @PostMapping("openbiz")
    @Operation(summary = "商务文件专家可看")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> openBiz(Authentication principal, @Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo) {
        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        String username = authInfo.getUser().getUsername();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        String user = realName+"_"+department;
        Integer userId = authInfo.getUser().getId();
        Integer meetingId = vo.getId();
        String message = "系统消息：已开启技术商务标，请各位点击刷新按钮，在文档列表中可查看，下载相关技术文件";
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(realName+"_"+department);
        cgChatRecordVo.setContent(message);
        cgChatRecordVo.setSendTime(LocalDateTime.now());
        cgChatRecordVo.setMessageType("KQSW");
        cgChatRecordVo.setProjectExecutionId(vo.getProjectExecution().getId());

        CgMeetingDetailsDto saveDto = cgMeetingMapper.toSaveDto(vo);
        Integer id = cgMeetingFacade.openBiz(saveDto);
        simpMessagingTemplate.convertAndSend("/topic/meeting/" +meetingId,cgChatRecordVo);
        Integer id2 = cgChatRecordFacade.save(user,userId,meetingId,message);
        return ResponseEntity.ok(id);
    }

    @ResponseBody
    @PostMapping("savequalauditfirst")
    @Operation(summary = "生成资格性审查")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> saveQualAuditFirst(@Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        Integer execId = vo.getProjectExecution().getId();

        // 查询供应商
        List<CgProjectExecutionSupDto> suppliers = cgProjectExecutionFacade.getSuppliersById(execId);
        CgBiddingDocumentDto biddingDoc = cgBiddingDocumentFacade.getByExecutionId(execId);
        CgBiddingDocumentDetailsDto biddingDocDetails = cgBiddingDocumentFacade.getDetailsById(biddingDoc.getId());
        CgConfereeQueryCriteria queryCriteria = new CgConfereeQueryCriteria();
        queryCriteria.setType("PBZJ");
        queryCriteria.setMeetingId(vo.getId());
        List<CgMeetingConfereeDto> ConfereeOfExperts = cgMeetingFacade.confereeListExpert(queryCriteria);
        List<CgBiddingDocumentExaminationDto> biddingDocQualExams = biddingDocDetails.getBiddingDocumentQualExaminations();

        Integer id = cgMeetingFacade.saveQualExamFirst(suppliers,biddingDocQualExams,ConfereeOfExperts,biddingDocDetails.getId());

        return ResponseEntity.ok(id);
    }

    @ResponseBody
    @GetMapping("listqualexam")
    @Operation(summary = "查询资格性审查")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<CgAuditQualConvertVo> listQualAudit(@Parameter(description = "采购方案执行ID") @RequestParam Integer id,@Parameter(description = "用户Id") @RequestParam Integer createdUserId) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgAuditQualConvertVo vo = new CgAuditQualConvertVo();
        setAuditInfo(vo,id,createdUserId);
        return ResponseEntity.ok(vo);
    }

    @ResponseBody
    @PostMapping("savequalexam")
    @Operation(summary = "填写资格性审查")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> saveQualExam(@RequestBody CgAuditQualConvertVo vo,Authentication principal, Integer meetingId, Boolean qualPass) {
        Object auditQualInfo = vo.getAuditQualInfo();
        if(auditQualInfo instanceof LinkedHashMap){
            LinkedHashMap collect1 = (LinkedHashMap) auditQualInfo;
            Set<String> keySet = collect1.keySet();
            ArrayList suppliers = new ArrayList();
            ArrayList qualAudit = new ArrayList();

            for (String s : keySet) {
                if(s.equals("suppliers")){
                    suppliers = (ArrayList) collect1.get(s);
                }else if(s.equals("qualAudit")){
                    qualAudit = (ArrayList) collect1.get(s);
                }
            }
            for (Object o : qualAudit) {
                if (auditQualInfo instanceof LinkedHashMap) {
                    LinkedHashMap collect2 = (LinkedHashMap) o;
                    Set<String> keySet2 = collect2.keySet();
                    ArrayList qualAuditItems = new ArrayList();
                    for (String s : keySet2) {
                        if (s.equals("auditQualItems")) {
                            qualAuditItems = (ArrayList) collect2.get(s);
                        }

                        for (Object qualAuditItem : qualAuditItems) {
                            CgAuditQualificationDto AuditQualificationDto = new CgAuditQualificationDto();
                            if (qualAuditItem instanceof LinkedHashMap) {
                                LinkedHashMap collect3 = (LinkedHashMap) qualAuditItem;
                                Set<String> keySet3 = collect3.keySet();
                                Integer i = 1;
                                for (String s1 : keySet3) {
                                    if (i == 1) {
                                        AuditQualificationDto.setId((Integer)(collect3.get(s1)));
                                    } else if (i == 2) {
                                        AuditQualificationDto.setPass((Boolean) (collect3.get(s1)));
                                        if(AuditQualificationDto.getId()!=null&&AuditQualificationDto.getPass()!=null){
                                            Integer id = cgMeetingFacade.saveQualExam(AuditQualificationDto);                                        }
                                    }
                                    i++;
                                }
                            }
                        }
                    }
                }
            }
        }
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        Integer userId = authInfo.getUser().getId();
        CgMeetingConfereeDto conferee = cgMeetingFacade.getConfereeByUserIdAndMeetingId(userId, meetingId);
        conferee.setQualCommit("YTJ");
        conferee.setQualCommitTime(LocalDateTime.now());
        conferee.setQualPass(qualPass);
        Integer savedId = cgMeetingFacade.saveMeetingConferee(conferee);
        return ResponseEntity.ok(savedId);
    }

    @ResponseBody
    @PostMapping("savecommitstatus")
    @Operation(summary = "更改审查提交状态")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> savecommitstatus(Principal principal) {
        CgMeetingConfereeDto conferee = cgMeetingFacade.getConfereeByUserIdAndMeetingId(4, 1);
        conferee.setQualCommit("YTJ");
        conferee.setQualCommitTime(LocalDateTime.now());
        Integer savedId = cgMeetingFacade.saveMeetingConferee(conferee);
        return ResponseEntity.ok(savedId);
    }

    @ResponseBody
    @PostMapping("saveExamauditfirst")
    @Operation(summary = "生成响应性审查")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> saveExamAuditFirst(@Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo) {
        Integer execId = vo.getProjectExecution().getId();

        // 查询供应商
        List<CgProjectExecutionSupDto> suppliers = cgProjectExecutionFacade.getSuppliersById(execId);
        CgBiddingDocumentDto biddingDoc = cgBiddingDocumentFacade.getByExecutionId(execId);
        CgBiddingDocumentDetailsDto biddingDocDetails = cgBiddingDocumentFacade.getDetailsById(biddingDoc.getId());
        CgConfereeQueryCriteria queryCriteria = new CgConfereeQueryCriteria();
        queryCriteria.setType("PBZJ");
        queryCriteria.setMeetingId(vo.getId());
        List<CgMeetingConfereeDto> ConfereeOfExperts = cgMeetingFacade.confereeListExpert(queryCriteria);
        List<CgBiddingDocumentExaminationDto> biddingDocRespExams = biddingDocDetails.getBiddingDocumentRespExaminations();
        Integer id = cgMeetingFacade.saveRespExamFirst(suppliers,biddingDocRespExams,ConfereeOfExperts,biddingDocDetails.getId());
        return ResponseEntity.ok(id);
    }

    @ResponseBody
    @GetMapping("listauditresp")
    @Operation(summary = "查询响应性审查")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<CgAuditRespConvertVo> listAuditResponse(@Parameter(description = "采购方案执行ID") @RequestParam Integer id,@Parameter(description = "专家ID") @RequestParam Integer createdUserId) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgAuditRespConvertVo vo = new CgAuditRespConvertVo();
        setAuditInfo(vo,id,createdUserId);
        return ResponseEntity.ok(vo);
    }

    @ResponseBody
    @PostMapping("saveauditresp")
    @Operation(summary = "填写响应性审查")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> saveRespExam(@RequestBody CgAuditRespConvertVo vo ,Authentication principal, Integer meetingId, Boolean respPass) {
        Object auditQualInfo = vo.getAuditQualInfo();
        if (auditQualInfo instanceof LinkedHashMap) {
            LinkedHashMap collect1 = (LinkedHashMap) auditQualInfo;
            Set<String> keySet = collect1.keySet();
            ArrayList suppliers = new ArrayList();
            ArrayList qualAudit = new ArrayList();

            for (String s : keySet) {
                if (s.equals("suppliers")) {
                    suppliers = (ArrayList) collect1.get(s);
                } else if (s.equals("qualAudit")) {
                    qualAudit = (ArrayList) collect1.get(s);
                }
            }
            for (Object o : qualAudit) {
                if (auditQualInfo instanceof LinkedHashMap) {
                    LinkedHashMap collect2 = (LinkedHashMap) o;
                    Set<String> keySet2 = collect2.keySet();
                    ArrayList qualAuditItems = new ArrayList();
                    for (String s : keySet2) {
                        if (s.equals("auditQualItems")) {
                            qualAuditItems = (ArrayList) collect2.get(s);
                        }

                        for (Object qualAuditItem : qualAuditItems) {
                            CgAuditResponseDto auditResponseDto = new CgAuditResponseDto();
                            if (qualAuditItem instanceof LinkedHashMap) {
                                LinkedHashMap collect3 = (LinkedHashMap) qualAuditItem;
                                Set<String> keySet3 = collect3.keySet();
                                int i = 1;
                                for (String s1 : keySet3) {
                                    if (i == 1) {
                                        auditResponseDto.setId((Integer) (collect3.get(s1)));
                                    } else if (i == 2) {
                                        auditResponseDto.setPass((Boolean) (collect3.get(s1)));
                                        if (auditResponseDto.getId() != null && auditResponseDto.getPass() != null) {
                                            Integer id = cgMeetingFacade.saveRespExam(auditResponseDto);
                                        }
                                    }
                                    i++;
                                }
                            }
                        }
                    }
                }
            }
        }
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        Integer userId = authInfo.getUser().getId();
        CgMeetingConfereeDto conferee = cgMeetingFacade.getConfereeByUserIdAndMeetingId(userId, meetingId);
        conferee.setRespCommit("YTJ");
        conferee.setRespPass(respPass);
        conferee.setRespCommitTime(LocalDateTime.now());
        Integer savedId = cgMeetingFacade.saveMeetingConferee(conferee);
        return ResponseEntity.ok(savedId);
    }

    @ResponseBody
    @PostMapping("saveGradeSupfirst")
    @Operation(summary = "生成专家评分")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> saveGradeSupFirst(@Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo) {
        Integer projId = vo.getProjId().getId();
        Integer execId = vo.getProjectExecution().getId();
        Integer meetingId = vo.getId();

        // 查询供应商
        List<CgProjectExecutionSupDto> suppliers = cgProjectExecutionFacade.getValidSuppliers(execId);

        // 查询评分大项
        List<CgProjectEvalDto> projectEvals = cgProjectFacade.getDetailsById(projId).getProjectEvals();

//        CgBiddingDocumentDetailsDto biddingDoc = cgBiddingDocumentFacade.getDetailsById(projId);
        CgConfereeQueryCriteria queryCriteria = new CgConfereeQueryCriteria();
        queryCriteria.setMeetingId(vo.getId());
        queryCriteria.setType("PBZJ");
        List<CgMeetingConfereeDto> ConfereeOfExperts = cgMeetingFacade.confereeListExpert(queryCriteria);
//        List<CgBiddingDocumentExaminationDto> biddingDocRespExams = biddingDoc.getBiddingDocumentRespExaminations();
        Integer id = cgMeetingFacade.saveGradeSupSumFirst(suppliers,projectEvals,ConfereeOfExperts,projId,meetingId);
        return ResponseEntity.ok(id);
    }

    @ResponseBody
    @GetMapping("listgradesup")
    @Operation(summary = "查询专家评分")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<CgGradeSupConvertVo> listGradeSup(Authentication principal, @Parameter(description = "采购方案ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgGradeSupConvertVo vo = new CgGradeSupConvertVo();
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        Integer userId = authInfo.getUser().getId();
         setGradeInfo(vo,id,userId);
        return ResponseEntity.ok(vo);
    }

    /**
     * 查询评分汇总
     * @param queryCriteria
     * @return
     */
    @ResponseBody
    @GetMapping("gradelist")
    @Operation(summary = "评分汇总列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<List<CgGradeSupSumVo>> gradelist(@ParameterObject CgGradeSupSumQueryCriteria queryCriteria) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        List<CgGradeSupSumDto> result = cgMeetingFacade.listGradeSupSum(queryCriteria,Sort.by("ranking").ascending());
        return ResponseEntity.ok(result.stream().map(cgGradeSupSumMapper::toVo).collect(Collectors.toList()));
    }

    @ResponseBody
    @PostMapping("saveVotefirst")
    @Operation(summary = "生成专家表决")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> saveVoteFirst(@Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo) {
        Integer projId = vo.getProjId().getId();
        Integer execId = vo.getProjectExecution().getId();
        Integer meetingId = vo.getId();

        // 查询供应商
        List<CgProjectExecutionSupDto> suppliers = cgProjectExecutionFacade.getValidSuppliers(execId);

        CgConfereeQueryCriteria queryCriteria = new CgConfereeQueryCriteria();
        queryCriteria.setMeetingId(vo.getId());
        queryCriteria.setType("PBZJ");
        List<CgMeetingConfereeDto> ConfereeOfExperts = cgMeetingFacade.confereeListExpert(queryCriteria);
        Integer id = cgMeetingFacade.saveVoteSumFirst(suppliers,ConfereeOfExperts,projId,meetingId);
        return ResponseEntity.ok(id);
    }


    @ResponseBody
    @GetMapping("listVote")
    @Operation(summary = "查询专家表决")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<List<CgGradeSupVo>> listVote(Authentication principal, @Parameter(description = "采购方案ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
//        CgGradeSupConvertVo vo = new CgGradeSupConvertVo();
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        Integer userId = authInfo.getUser().getId();
//        setGradeInfo(vo,id,userId);
        CgGradeSupQueryCriteria queryCriteria = new CgGradeSupQueryCriteria();
        queryCriteria.setProjectId(id);
        queryCriteria.setUserId(userId);
        List<CgGradeSupDto> gradeSups = cgMeetingFacade.listGradeSup(queryCriteria);
        List<CgGradeSupVo> gradeSupVos = new ArrayList<>();

        for (CgGradeSupDto gradeSup : gradeSups) {
            CgGradeSupVo cgGradeSupVo = cgGradeSupMapper.toVo(gradeSup);
            gradeSupVos.add(cgGradeSupVo);
        }
//        CgGradeSupVo cgGradeSupVo = cgGradeSupMapper.toVo(gradeSups);
        return ResponseEntity.ok(gradeSupVos);
    }

    @ResponseBody
    @PostMapping("saveVote")
    @Operation(summary = "填写专家评分")
    public ResponseEntity<Integer> saveGradeSup(@RequestBody List<CgGradeSupVo> vo) {
        List<CgGradeSupDto> saveDto = cgGradeSupMapper.toSaveDto(vo);
        cgMeetingFacade.saveVote(saveDto);
        return ResponseEntity.ok(200);
    }


    @ResponseBody
    @PostMapping("meetingTwice")
    @Operation(summary = "二次评审")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> MeetingTwice(@Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo) {
        CgMeetingDetailsDto saveDto = cgMeetingMapper.toSaveDto(vo);
        Integer id = cgMeetingFacade.meetingTwice(saveDto);
        return ResponseEntity.ok(id);
    }

    @ResponseBody
    @GetMapping("getgradedetailsbyid")
    @Operation(summary = "id查询评分汇总详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<CgGradeSupSumVo> getGradeDetailsById(@Parameter(description = "评分汇总主表ID") @RequestParam Integer id) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgGradeSupSumDetailsDto result = cgMeetingFacade.getGradeDetailsById(id);
        return ResponseEntity.ok(cgGradeSupSumMapper.toVo(result));
    }

    @ResponseBody
    @PostMapping("gradesumup")
    @Operation(summary = "评分汇总")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> gradesumup(@ParameterObject CgGradeSupSumQueryCriteria queryCriteria) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
//        List<CgGradeSupSumDetailsDto> result = cgMeetingFacade.listGradeSupSum(queryCriteria);
//        return ResponseEntity.ok(result.stream().map(cgGradeSupSumMapper::toVo).collect(Collectors.toList()));

        List<CgGradeSupSumDetailsDto> result = cgMeetingFacade.listGradeSupSumDetails(queryCriteria);
        for (CgGradeSupSumDetailsDto cgGradeSupSumDetailsDto : result) {
            Double scoreSum = 0.0;
            List<CgGradeSupDto> gradeSups = cgGradeSupSumDetailsDto.getGradeSups();
            for (CgGradeSupDto gradeSup : gradeSups) {
                scoreSum+=gradeSup.getScore();
            }
            Double score = scoreSum/gradeSups.size();
            cgMeetingFacade.saveGradeSupSum(score,cgGradeSupSumDetailsDto.getId());
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "sumScore");
        List<CgGradeSupSumDetailsDto> resultSorted = cgMeetingFacade.listGradeSupSumDetails(queryCriteria,sort);
        for (int i = 0; i < resultSorted.size(); i++) {
            cgMeetingFacade.saveGradeSupSumRanking(i+1,resultSorted.get(i).getId());
            cgMeetingFacade.saveGradeSupSumIsSum(true,resultSorted.get(i).getId());
        }
        cgMeetingFacade.updateFinishGrade(true,queryCriteria.getMeetingId());

        return ResponseEntity.ok(200);
    }

    @ResponseBody
    @PostMapping("votesumup")
    @Operation(summary = "表决汇总")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> votesumup(@ParameterObject CgGradeSupSumQueryCriteria queryCriteria) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        List<CgGradeSupSumDetailsDto> result = cgMeetingFacade.listGradeSupSumDetails(queryCriteria);
        for (CgGradeSupSumDetailsDto cgGradeSupSumDetailsDto : result) {
            Double scoreSum = 0.0;
            List<CgGradeSupDto> gradeSups = cgGradeSupSumDetailsDto.getGradeSups();
            for (CgGradeSupDto gradeSup : gradeSups) {
                scoreSum+=gradeSup.getScore();
            }
            Double score = scoreSum;
            cgMeetingFacade.saveGradeSupSum(score,cgGradeSupSumDetailsDto.getId());
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "sumScore");
        List<CgGradeSupSumDetailsDto> resultSorted = cgMeetingFacade.listGradeSupSumDetails(queryCriteria,sort);
        for (int i = 0; i < resultSorted.size(); i++) {
            cgMeetingFacade.saveGradeSupSumRanking(i+1,resultSorted.get(i).getId());
            cgMeetingFacade.saveGradeSupSumIsSum(true,resultSorted.get(i).getId());
        }

        return ResponseEntity.ok(200);
    }

    @ResponseBody
    @PostMapping("saveGradeSup")
    @Operation(summary = "填写专家评分")
    public ResponseEntity<Integer> saveGradeSup(@RequestBody CgGradeSupConvertVo vo ,Authentication principal, Integer meetingId) {
        Object gradeInfo = vo.getGradeInfo();
        if (gradeInfo instanceof LinkedHashMap) {
            LinkedHashMap collect1 = (LinkedHashMap) gradeInfo;
            Set<String> keySet = collect1.keySet();
            ArrayList suppliers = new ArrayList();
            ArrayList qualAudit = new ArrayList();

            for (String s : keySet) {
                if (s.equals("suppliers")) {
                    suppliers = (ArrayList) collect1.get(s);
                } else if (s.equals("gradeSups")) {
                    qualAudit = (ArrayList) collect1.get(s);
                }
            }
            for (Object o : qualAudit) {
                if (gradeInfo instanceof LinkedHashMap) {
                    LinkedHashMap collect2 = (LinkedHashMap) o;
                    Set<String> keySet2 = collect2.keySet();
                    ArrayList gradeItems = new ArrayList();
                    for (String s : keySet2) {
                        if (s.equals("gradeItems")) {
                            gradeItems = (ArrayList) collect2.get(s);
                        }

                        for (Object qualAuditItem : gradeItems) {
                            CgGradeSupDto gradeSupDto = new CgGradeSupDto();
                            if (qualAuditItem instanceof LinkedHashMap) {
                                LinkedHashMap collect3 = (LinkedHashMap) qualAuditItem;
                                Set<String> keySet3 = collect3.keySet();
                                int i = 1;
                                for (String s1 : keySet3) {
                                    if (i == 1) {
                                        gradeSupDto.setId((Integer) (collect3.get(s1)));
                                    } else if (i == 2) {
                                        gradeSupDto.setScore((Integer) (collect3.get(s1)));
                                        if (gradeSupDto.getId() != null && gradeSupDto.getScore() != null) {
                                            cgMeetingFacade.saveGradeSup(gradeSupDto.getScore(),gradeSupDto.getId());
                                        }
                                    }
                                    i++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return ResponseEntity.ok(200);
    }

    /**
     * 获取历史信息
     * @param meetingId
     */
    @ResponseBody
    @GetMapping("/cg/meeting/history")
    @Operation(summary = "查询历史信息")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<List<CgChatRecordVo>> getHistory(Principal principal,
                                                           @DestinationVariable Integer meetingId) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        List<CgChatRecordSaveDto> result = cgChatRecordFacade.list(meetingId);
        return ResponseEntity.ok(result.stream().map(cgChatRecordMapper::toVo).collect(Collectors.toList()));
    }

    /**
     * 更改参会人员资格汇总状态
     * @param queryCriteria
     * @return
     */
    @ResponseBody
    @PostMapping("savequalcollstatus")
    @Operation(summary = "更改资格汇总状态")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> saveQualcollStatus(@ParameterObject CgConfereeQueryCriteria queryCriteria) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        queryCriteria.setType("PBZJ");
        List<CgMeetingConfereeDto> cgMeetingConfereeDtos = cgMeetingFacade.confereeListExpert(queryCriteria);
        for (CgMeetingConfereeDto cgMeetingConfereeDto : cgMeetingConfereeDtos) {
            cgMeetingConfereeDto.setQualCommit("YHZ");
            Integer savedId = cgMeetingFacade.saveMeetingConferee(cgMeetingConfereeDto);
        }
        return ResponseEntity.ok(200);
    }

    /**
     * 更改参会人员响应汇总状态
     * @param queryCriteria
     * @return
     */
    @ResponseBody
    @PostMapping("saverespcollectstatus")
    @Operation(summary = "更改响应汇总状态")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> saveRespCollectStatus(@ParameterObject CgConfereeQueryCriteria queryCriteria) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        queryCriteria.setType("PBZJ");
        List<CgMeetingConfereeDto> cgMeetingConfereeDtos = cgMeetingFacade.confereeListExpert(queryCriteria);
        for (CgMeetingConfereeDto cgMeetingConfereeDto : cgMeetingConfereeDtos) {
            cgMeetingConfereeDto.setRespCommit("YHZ");
            Integer savedId = cgMeetingFacade.saveMeetingConferee(cgMeetingConfereeDto);
        }
        return ResponseEntity.ok(200);
    }

    /**
     * 查询参会专家详情
     * @param userId
     * @param meetingId
     * @return
     */
    @ResponseBody
    @GetMapping("getbyuseridandmeetingid")
    @Operation(summary = "查询参会专家详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<CgMeetingConfereeVo> getDetailsByUserId(@Parameter(description = "用户ID") @RequestParam Integer userId, @Parameter(description = "会议ID")@RequestParam Integer meetingId) {
        dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        CgMeetingConfereeDto result = cgMeetingFacade.getConfereeByUserIdAndMeetingId(userId, meetingId);
        return ResponseEntity.ok(cgMeetingConfereeMapper.toVo(result));
    }

    @ResponseBody
    @PostMapping("updatecontent")
    @Operation(summary = "更新会议决议内容")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> updateContent(Authentication principal, @Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo, @RequestParam String content) {
        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        String username = authInfo.getUser().getUsername();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        String user = realName+"_"+department;
        Integer userId = authInfo.getUser().getId();
        Integer projExecId = vo.getProjectExecution().getId();
        Integer meetingId = vo.getId();
        Integer savedId = cgMeetingFacade.updateResoCont(meetingId,content);
        String message = "系统消息：（评审会决议）主持人发布评标定标结果，专家请表决。";
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(realName+"_"+department);
        cgChatRecordVo.setContent(message);
        cgChatRecordVo.setSendTime(LocalDateTime.now());
        cgChatRecordVo.setMessageType("JYNR");
        CgProjectExecutionDetailsDto result = cgProjectExecutionFacade.getDetailsById(projExecId);
        cgChatRecordVo.setProjectExecutionId(result.getId());

        CgMeetingDetailsDto saveDto = cgMeetingMapper.toSaveDto(vo);
        simpMessagingTemplate.convertAndSend("/topic/meeting/" +meetingId,cgChatRecordVo);
        cgChatRecordFacade.save(user,userId,meetingId,message);
        return ResponseEntity.ok(savedId);
    }

    @ResponseBody
    @PostMapping("opengrade")
    @Operation(summary = "开启评分(开启表决)")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> openGrade(Authentication principal, @Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo) {
        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        String username = authInfo.getUser().getUsername();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        String user = realName+"_"+department;
        Integer userId = authInfo.getUser().getId();
        Integer projExecId = vo.getProjectExecution().getId();
        Integer meetingId = vo.getId();
        Integer savedId = cgMeetingFacade.updateIsGraded(true,meetingId);
        String message;
        if(Objects.equals(vo.getProjId().getEvalMethod(), "ZHPFF") || Objects.equals(vo.getProjId().getEvalMethod(), "XJBF")){
            message = "系统消息：主持人现已开启评分功能，专家请进行评分。";
        }else {
            message = "系统消息：主持人现已开启表决功能，专家请进行表决。";
        }
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(realName+"_"+department);
        cgChatRecordVo.setContent(message);
        cgChatRecordVo.setSendTime(LocalDateTime.now());
        cgChatRecordVo.setMessageType("KQPF");
        CgProjectExecutionDetailsDto result = cgProjectExecutionFacade.getDetailsById(projExecId);
        cgChatRecordVo.setProjectExecutionId(result.getId());

        simpMessagingTemplate.convertAndSend("/topic/meeting/" +meetingId,cgChatRecordVo);
        cgChatRecordFacade.save(user,userId,meetingId,message);
        return ResponseEntity.ok(savedId);
    }

    /**
     * 更新专家是否同意决议
     * @param id 会议id
     * @return
     */
    @ResponseBody
    @PostMapping("updateexpcontent")
    @Operation(summary = "更新专家是否同意决议")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> updateContent(Authentication principal, @Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo, @RequestParam Integer id, @RequestParam Integer content) {
        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        String username = authInfo.getUser().getUsername();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        String user = realName+"_"+department;
        Integer userId = authInfo.getUser().getId();
        Integer projExecId = vo.getProjectExecution().getId();
        Integer meetingId = vo.getId();
        String message = "系统消息：专家已表决";
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(realName+"_"+department);
        cgChatRecordVo.setContent(message);
        cgChatRecordVo.setSendTime(LocalDateTime.now());
        cgChatRecordVo.setMessageType("SFTYJY");//是否同意决议
        CgProjectExecutionDetailsDto result = cgProjectExecutionFacade.getDetailsById(projExecId);
        cgChatRecordVo.setProjectExecutionId(result.getId());

        CgMeetingDetailsDto saveDto = cgMeetingMapper.toSaveDto(vo);
        simpMessagingTemplate.convertAndSend("/topic/meeting/" +meetingId,cgChatRecordVo);
        cgChatRecordFacade.save(user,userId,meetingId,message);
        return ResponseEntity.ok(cgMeetingFacade.updateMeetingConferee(id,content));
    }

    /**
     * 更新专家签到
     * @param id 会议id
     * @return
     */
    @ResponseBody
    @PostMapping("updateexpSignIn")
    @Operation(summary = "更新专家签到")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.core.MeetingController).MENU_CODE_VIEW)")
    public ResponseEntity<Integer> updateContent(Authentication principal, @Validated(CgMeetingVo.Save.class) @RequestBody CgMeetingVo vo, @RequestParam Integer id) {
        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        String username = authInfo.getUser().getUsername();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        String user = realName+"_"+department;
        Integer userId = authInfo.getUser().getId();
        Integer projExecId = vo.getProjectExecution().getId();
        Integer meetingId = vo.getId();
        String message = "系统消息：专家"+realName+"已签到";
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(realName+"_"+department);
        cgChatRecordVo.setContent(message);
        cgChatRecordVo.setSendTime(LocalDateTime.now());
        cgChatRecordVo.setMessageType("ZJQD");//是否同意决议
        cgChatRecordVo.setProjectExecutionId(projExecId);

        CgMeetingDetailsDto saveDto = cgMeetingMapper.toSaveDto(vo);
        simpMessagingTemplate.convertAndSend("/topic/meeting/" +meetingId,cgChatRecordVo);
        cgChatRecordFacade.save(user,userId,meetingId,message);
        return ResponseEntity.ok(cgMeetingFacade.updateConfereeSignIn(id));
    }

    @ResponseBody
    @GetMapping("getreplydetailsbyreplyid")
    @Operation(summary = "查询报价应答详情")
    public ResponseEntity<CgBusinessReplyVo> getReplyDetailsByReplyId(@Parameter(description = "应答ID") @RequestParam Integer replyId) {
        CgBusinessReplyDetailsDto result = cgBusinessReplyFacade.getDetailsById(replyId);
        return ResponseEntity.ok(cgProjectExecutionMapper.toVo(result));
    }

    /**
     * 设置应答报价信息
     * @param executionVo
     */
    private void setReplyInfo(CgProjectExecutionVo executionVo,CgMeetingVo meetingVo) {
        try {
            CgBusinessReplyQueryCriteria queryCriteria = new CgBusinessReplyQueryCriteria();
            queryCriteria.setBusinessProjectId(executionVo.getProject().getId().toString());
            queryCriteria.setNeedQuote(true);
            List<CgBusinessReplyDto> replies = cgBusinessReplyFacade
                    .list(queryCriteria);
            if (replies != null && !replies.isEmpty()) {
                int round = replies.stream().mapToInt(CgBusinessReplyDto::getQuoteRound).max().orElse(0);
                if (round > 0) {
                    ObjectNode replyInfo = objectMapper.createObjectNode();
                    replyInfo.put("round",round);
                    ArrayNode replyInfoData = replyInfo.putArray("data");
                    Map<Integer, List<CgBusinessReplyDto>> collect =
                            replies.stream().collect(Collectors.groupingBy(CgBusinessReplyDto::getSupplierId));
                    collect.forEach((k, v) -> {
                        ObjectNode datum = replyInfoData.addObject();
                        datum.put("supplierId",k);
                        executionVo.getProjectExecutionSups().stream()
                                .filter(e -> Objects.nonNull(e.getSupplier()))
                                .filter(e -> Objects.equals(k,e.getSupplier().getId())).findFirst()
                                .ifPresent(e -> datum.put("supplierName",e.getSupplier().getName()));
                        for (CgBusinessReplyDto cgBusinessReplyDto : v) {
                            datum.put("reply_" + cgBusinessReplyDto.getQuoteRound(),cgBusinessReplyDto.getId());
                            datum.put("reply_" + cgBusinessReplyDto.getQuoteRound() + "_totalPrice",cgBusinessReplyDto.getTotalPrice());
                        }
                    });
                    meetingVo.setReplyInfo(replyInfo);
                }
            }
        } catch (Exception e) {
            log.error("查询供应商报价信息出错",e);
        }
    }

    // ---------------------------WebSocket接口-------------------------------
    /**
     * 进入会议室
     * @param principal
     * @param meetingId
     */
    @MessageMapping("/cg/meeting/{meetingId}/enter")
    public String enter(Authentication principal,
                        @DestinationVariable Integer meetingId){

        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        String user = realName+"_"+department;
        Integer userId = authInfo.getUser().getId();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(user);
        cgChatRecordVo.setContent(realName+"加入会议");
        cgChatRecordVo.setMessageType("ENTER");
        cgChatRecordVo.setSendTime(LocalDateTime.now());
        simpMessagingTemplate.convertAndSend("/topic/meeting/" + meetingId,cgChatRecordVo);
        Integer id = cgChatRecordFacade.save(user,userId,meetingId,realName+"加入会议");

        simpMessagingTemplate.convertAndSendToUser(principal.getName(),"/queue/msg","haha2");
        return "aabb";
    }

    /**
     * 退出会议室
     * @param principal
     * @param meetingId
     */
    @MessageMapping("/cg/meeting/{meetingId}/exit")
    public String exit(Authentication principal,
                        @DestinationVariable Integer meetingId){
        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        String user = realName+"_"+department;
        Integer userId = authInfo.getUser().getId();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(realName+"_"+department);
        cgChatRecordVo.setContent(realName+"退出会议");
        cgChatRecordVo.setMessageType("EXIT");
        cgChatRecordVo.setSendTime(LocalDateTime.now());
        simpMessagingTemplate.convertAndSend("/topic/meeting/" + meetingId,cgChatRecordVo);
        Integer id = cgChatRecordFacade.save(user,userId,meetingId,realName+"退出会议");
        simpMessagingTemplate.convertAndSendToUser(principal.getName(),"/queue/msg","success");
        return "success";

    }

    /**
     * 发言
     * @param message
     */
    @MessageMapping("/cg/meeting/{meetingId}/say")
    public void send(Authentication principal,
                     @DestinationVariable Integer meetingId,
                     String message) {
        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        String user = realName+"_"+department;
        Integer userId = authInfo.getUser().getId();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(realName+"_"+department);
        cgChatRecordVo.setContent(message);
        cgChatRecordVo.setSendTime(LocalDateTime.now());
        simpMessagingTemplate.convertAndSend("/topic/meeting/" + meetingId,cgChatRecordVo);
        Integer id = cgChatRecordFacade.save(user,userId,meetingId,message);
    }

    /**
     * 资格性审查列表
     * @param vo
     */
    private void setAuditInfo(CgAuditQualConvertVo vo,Integer id,Integer createdUserId) {
        try {
            CgBiddingDocumentDto biddingDocument = cgBiddingDocumentFacade.getByExecutionId(id);
            Integer biddingDocumentId = biddingDocument.getId();
            CgBiddingDocumentDetailsDto biddingDocumentDetails = cgBiddingDocumentFacade.getDetailsById(biddingDocumentId);
            CgAuditQualQueryCriteria queryCriteria = new CgAuditQualQueryCriteria();
            queryCriteria.setBiddingDocumentId(biddingDocumentId);
            queryCriteria.setExpertId(createdUserId);
            queryCriteria.setMeetingId(id);

            List<CgAuditQualificationDto> cgAuditQualificationDtos = cgMeetingFacade.listQualexam(queryCriteria);

            if (cgAuditQualificationDtos != null && !cgAuditQualificationDtos.isEmpty()) {
                int round = cgAuditQualificationDtos.stream().mapToInt(CgAuditQualificationDto::getSupplier).sum();
                if (round > 0) {
                    ObjectNode replyInfo = objectMapper.createObjectNode();
                    replyInfo.put("round", round);
                    ArrayNode replyInfoData = replyInfo.putArray("qualAudit");
                    ArrayNode replyInfoData2 = replyInfo.putArray("suppliers");
                    Map<String, List<CgAuditQualificationDto>> collect1 =
                            cgAuditQualificationDtos.stream().collect(Collectors.groupingBy(CgAuditQualificationDto::getSubCode));
                    collect1.forEach((k, v) -> {
                        ObjectNode datum = replyInfoData.addObject();
                        datum.put("subCode", k);
                        biddingDocumentDetails.getBiddingDocumentQualExaminations().stream()
                                .filter(e -> Objects.nonNull(e.getSubCode()))
                                .filter(e -> Objects.equals(k, e.getSubCode())).findFirst()
                                .ifPresent(e -> datum.put("subName", e.getSubName())
                                        .put("code", e.getCode())
                                        .put("name", e.getName()));
                        int i = 1;
                        for (CgAuditQualificationDto cgAuditQualification : v) {
//                            datum.put("reply_" + cgAuditQualification.getSupplier(),cgAuditQualification.getId());
                            datum.put("reply_" + i, cgAuditQualification.getId());
                            datum.put("reply_" + i + "_isPass", cgAuditQualification.getPass());
                            i++;
                        }
                    });

                    Map<Integer, List<CgAuditQualificationDto>> collect2 =
                            cgAuditQualificationDtos.stream().collect(Collectors.groupingBy(CgAuditQualificationDto::getSupplier));
                    collect2.forEach((k, v) -> {
                        ObjectNode datum = replyInfoData2.addObject();
                        datum.put("supplier", k);
                        for (CgAuditQualificationDto cgAuditQualification : v) {
                            CgSupplierDto supplier = cgSupplierFacade.getById(cgAuditQualification.getSupplier());
                            datum.put("supplier", supplier.getName());
                        }
                    });
                    vo.setAuditQualInfo(replyInfo);
                }
            }
        } catch (Exception e) {
            log.error("查询资格性审查列表出错",e);
        }
    }

    /**
     * 响应性审查列表
     * @param vo
     */
    private void setAuditInfo(CgAuditRespConvertVo vo,Integer id,Integer createdUserId) {
        try {
            CgBiddingDocumentDto biddingDocument = cgBiddingDocumentFacade.getByExecutionId(id);
            Integer biddingDocumentId = biddingDocument.getId();
            CgBiddingDocumentDetailsDto biddingDocumentDetails = cgBiddingDocumentFacade.getDetailsById(biddingDocumentId);
            CgAuditRespQueryCriteria queryCriteria = new CgAuditRespQueryCriteria();
            queryCriteria.setBiddingDocumentId(biddingDocumentId);
            queryCriteria.setExpertId(createdUserId);
            queryCriteria.setMeetingId(id);

            List<CgAuditResponseDto> cgAuditResponseDtos = cgMeetingFacade.listRespexam(queryCriteria);

            if (cgAuditResponseDtos != null && !cgAuditResponseDtos.isEmpty()) {
                int round = cgAuditResponseDtos.stream().mapToInt(CgAuditResponseDto::getSupplier).sum();
                if (round > 0) {
                    ObjectNode replyInfo = objectMapper.createObjectNode();
                    replyInfo.put("round", round);
                    ArrayNode replyInfoData = replyInfo.putArray("qualAudit");
                    ArrayNode replyInfoData2 = replyInfo.putArray("suppliers");
                    Map<String, List<CgAuditResponseDto>> collect1 =
                            cgAuditResponseDtos.stream().collect(Collectors.groupingBy(CgAuditResponseDto::getSubCode));
                    collect1.forEach((k, v) -> {
                        ObjectNode datum = replyInfoData.addObject();
                        datum.put("subCode", k);
                        biddingDocumentDetails.getBiddingDocumentRespExaminations().stream()
                                .filter(e -> Objects.nonNull(e.getSubCode()))
                                .filter(e -> Objects.equals(k, e.getSubCode())).findFirst()
                                .ifPresent(e -> datum.put("subName", e.getSubName())
                                        .put("code", e.getCode())
                                        .put("name", e.getName()));
                        int i = 1;
                        for (CgAuditResponseDto cgAuditResponseDto : v) {
                            datum.put("reply_" + i, cgAuditResponseDto.getId());
                            datum.put("reply_" + i + "_isPass", cgAuditResponseDto.getPass());
                            i++;
                        }
                    });

                    Map<Integer, List<CgAuditResponseDto>> collect2 =
                            cgAuditResponseDtos.stream().collect(Collectors.groupingBy(CgAuditResponseDto::getSupplier));
                    collect2.forEach((k, v) -> {
                        ObjectNode datum = replyInfoData2.addObject();
                        datum.put("supplier", k);
                        for (CgAuditResponseDto cgAuditResponseDto : v) {
                            CgSupplierDto supplier = cgSupplierFacade.getById(cgAuditResponseDto.getSupplier());
                            datum.put("supplier", supplier.getName());
                        }
                    });
                    vo.setAuditQualInfo(replyInfo);
                }
            }
        } catch (Exception e) {
            log.error("查询响应性审查列表出错",e);
        }
    }

    /**
     * 评委打分列表
     * @param vo
     */
    private void setGradeInfo(CgGradeSupConvertVo vo,Integer id,Integer userId) {
        try {
            CgGradeSupQueryCriteria queryCriteria = new CgGradeSupQueryCriteria();
            queryCriteria.setProjectId(id);
            queryCriteria.setUserId(userId);
            List<CgGradeSupDto> gradeSups = cgMeetingFacade.listGradeSup(queryCriteria);
            CgProjectDetailsDto projectDetails = cgProjectFacade.getDetailsById(id);
            List<CgProjectEvalDto> projectEvals = projectDetails.getProjectEvals();

            if (gradeSups != null && !gradeSups.isEmpty()) {
                int round = gradeSups.stream().mapToInt(CgGradeSupDto::getSupplier).sum();
                if (round > 0) {
                    ObjectNode replyInfo = objectMapper.createObjectNode();
                    replyInfo.put("round", round);
                    ArrayNode replyInfoData = replyInfo.putArray("gradeSups");
                    ArrayNode replyInfoData2 = replyInfo.putArray("suppliers");
                    Map<String, List<CgGradeSupDto>> collect1 =
                            gradeSups.stream().collect(Collectors.groupingBy(CgGradeSupDto::getSubCode));
                    collect1.forEach((k, v) -> {
                        ObjectNode datum = replyInfoData.addObject();
                        datum.put("subCode", k);

                        for (CgProjectEvalDto projectEval : projectEvals) {
                            projectEval.getProjectEvalRules().stream()
                                    .filter(e -> Objects.nonNull(e.getSubCode()))
                                    .filter(e -> Objects.equals(k, e.getSubCode())).findFirst()
                                    .ifPresent(e -> datum.put("subName", e.getSubName())
                                            .put("weight", e.getWeight())
                                            .put("note", e.getNote())
                                            .put("name", e.getName()));
                        }
                        int i = 1;
                        for (CgGradeSupDto cgAuditQualification : v) {
//                            datum.put("reply_" + cgAuditQualification.getSupplier(),cgAuditQualification.getId());
                            datum.put("reply_" + i, cgAuditQualification.getId());
                            datum.put("reply_" + i + "_isPass", cgAuditQualification.getScore());
                            i++;
                        }
                    });
                    Map<Integer, List<CgGradeSupDto>> collect2 =
                            gradeSups.stream().collect(Collectors.groupingBy(CgGradeSupDto::getSupplier));
                    collect2.forEach((k, v) -> {
                        ObjectNode datum = replyInfoData2.addObject();
                        datum.put("supplier", k);
                        for (CgGradeSupDto cgGradeSupDto : v) {
                            CgSupplierDto supplier = cgSupplierFacade.getById(cgGradeSupDto.getSupplier());
                            datum.put("supplier", supplier.getName());
                        }
                    });
                    vo.setGradeInfo(replyInfo);
                }
            }
        } catch (Exception e) {
            log.error("查询响应性审查列表出错",e);
        }
    }

    // ---------------------------WebSocket接口-------------------------------


    /**
     * 建立连接监听
     * @param sessionConnectedEvent
     * @throws Exception
     */
    @EventListener(classes = {SessionConnectedEvent.class})
    public void handleConnectListener(SessionConnectedEvent sessionConnectedEvent) throws Exception {
        log.info("handleConnectListener");
        Authentication principal = (Authentication)sessionConnectedEvent.getUser();
        RpcUtils.putRequestBaggage(principal);
        AuthInfo authInfo = (AuthInfo) principal.getPrincipal();
        CgChatRecordVo cgChatRecordVo = new CgChatRecordVo();
        String username = authInfo.getUser().getUsername();
        String realName = authInfo.getUser().getRealname();
        String department = authInfo.getUser().getDepartment().getName();
        Integer userId = authInfo.getUser().getId();
        cgChatRecordVo.setSenderUserId(userId);
        cgChatRecordVo.setSenderName(realName+"_"+department);
        cgChatRecordVo.setContent(username+"加入会议");
        cgChatRecordVo.setMessageType("ENTER");
        cgChatRecordVo.setSendTime(LocalDateTime.now());

        sessionConnectedEvent.getSource();

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.SUBSCRIBE);

        headerAccessor.setSessionId(sessionConnectedEvent.getMessage().getHeaders().get("simpSessionId", String.class));

        Message<byte[]> message = MessageBuilder.createMessage(JSON.toJSONString(cgChatRecordVo).getBytes(), headerAccessor.getMessageHeaders());
//        simpMessagingTemplate.convertAndSend("/topic/meeting/" +57,cgChatRecordVo);

        subProtocolWebSocketHandler.handleMessage(message);

    }

    /**
     * 处理断开连接
     * @param sessionDisconnectEvent
     * @throws Exception
     */
    @EventListener
    public void handleDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent) throws Exception {
        log.info("handleDisconnectListener");

    }

    /**
     * 订阅监听
     * @param sessionSubscribeEvent
     * @throws Exception
     */
    @EventListener
    public void handleSubscribeListener(SessionSubscribeEvent sessionSubscribeEvent) throws Exception {
        log.info("handleSubscribeListener");

    }

    /**
     * 取消订阅监听
     * @param sessionUnsubscribeEvent
     * @throws Exception
     */
    @EventListener
    public void handleUnsubscribeListener(SessionUnsubscribeEvent sessionUnsubscribeEvent) throws Exception {
        log.info("handleUnsubscribeListener");

    }

    private CgBusinessReplyDetailsDto decryptAtts(CgBusinessReplyDetailsDto list) {
        List<CgAttachmentDto> attUs = list.getAttUs();
        if (attUs != null) {
            for (CgAttachmentDto us : attUs) {
                FastByteArrayOutputStream out = new FastByteArrayOutputStream();
                fileStore.download(us.getPath(),out);
                ByteArrayResource resource = new ByteArrayResource(FileCipherUtils.decrypt(out.toByteArray()));
                String path = fileStore.upload("/biddoc", us.getName(), resource);
                us.setPath(path);
            }
        }
        return list;
    }
}
