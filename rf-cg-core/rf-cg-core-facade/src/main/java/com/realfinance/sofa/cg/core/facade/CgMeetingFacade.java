package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 会议
 */
public interface CgMeetingFacade {


    Page<CgMeetingDto> list(CgMeetingQueryCriteria queryCriteria, @NotNull Pageable pageable);

    Page<CgMeetingConfereeDto> confereeList(CgConfereeQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 查找类型为专家的参会人员
     * @param queryCriteria
     * @return
     */
    List<CgMeetingConfereeDto> confereeListExpert(CgConfereeQueryCriteria queryCriteria);

    Integer saveMeeting(Integer id,List<CgDrawExpertListDto> cgDrawExpertListDtos);

    Integer updateResoCont(Integer id,String content);

    Integer updateConfereeSignIn(Integer id);

    Integer updateIsGraded(Boolean isGraded, Integer id);

    Integer updateFinishGrade(Boolean finishGrade, Integer id);

    Integer updateMeetingConferee(Integer id,Integer content);

    Integer saveMeetingConferee(@NotNull CgMeetingConfereeDto confereeDto);

    Page<CgProjectExecutionSupDto> listSupplier(CgSupplierQueryCriteria queryCriteria, @NotNull Pageable pageable);

    List<CgProjectExecutionSupDto> listSupplier(CgSupplierQueryCriteria queryCriteria);

    /**
     * 评审会文件查询（即采购方案文件）
     * @param queryCriteria
     * @return
     */
    List<CgProjectExecutionAttDto> listFile(CgAttaFileQueryCriteria queryCriteria);

    /**
     * @param id 评审会议Id
     */
    CgMeetingDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * @param userId 评审会议userId
     */
//    CgMeetingConfereeDto getconfereeDetailsByUserId(@NotNull Integer userId,@NotNull Integer meetingId);

    /**
     * @param id 开启会议
     */
    Integer startMeeting(@NotNull Integer id);

    /**
     * @param id 结束会议
     */
    Integer endMeeting(@NotNull Integer id);

    /**
     * 查询到会专家
     * @param userId
     * @param meetingId
     * @return
     */
    CgMeetingConfereeDto getConfereeByUserIdAndMeetingId(@NotNull Integer userId,@NotNull Integer meetingId);


    /**
     * 价格文件专家可看
     * @param saveDto
     * @return
     */
    Integer openQuote(@NotNull CgMeetingDetailsDto saveDto);

    /**
     *查询资格性审查
     * @param queryCriteria
     * @return
     */
    List<CgAuditQualificationDto> listQualexam(CgAuditQualQueryCriteria queryCriteria);

    /**
     *查询响应性审查
     * @param queryCriteria
     * @return
     */
    List<CgAuditResponseDto> listRespexam(CgAuditRespQueryCriteria queryCriteria);

    /**
     *查询专家评分
     * @param queryCriteria
     * @return
     */
    List<CgGradeSupSumDto> listGradeSupSum(CgGradeSupSumQueryCriteria queryCriteria,Sort sort);

    /**
     *查询专家评分
     * @param queryCriteria
     * @return
     */
    List<CgGradeSupSumDetailsDto> listGradeSupSumDetails(CgGradeSupSumQueryCriteria queryCriteria);

    /**
     *按序查询专家评分
     * @param queryCriteria
     * @return
     */
    List<CgGradeSupSumDetailsDto> listGradeSupSumDetails(CgGradeSupSumQueryCriteria queryCriteria, Sort sort);


    /**
     * @param id 评审会议Id
     */
    CgGradeSupSumDetailsDto getGradeDetailsById(@NotNull Integer id);

    /**
     *查询专家评分子表
     * @param queryCriteria
     * @return
     */
    List<CgGradeSupDto> listGradeSup(CgGradeSupQueryCriteria queryCriteria);

    /**
     * 商务文件专家可看
     * @param saveDto
     * @return
     */
    Integer openBiz(@NotNull CgMeetingDetailsDto saveDto);

    Integer saveQualExamFirst(List<CgProjectExecutionSupDto> suppliers,List<CgBiddingDocumentExaminationDto> biddingDocQualExams,List<CgMeetingConfereeDto> ConfereeOfExperts,Integer biddingDocumentId);

    Integer saveRespExamFirst(List<CgProjectExecutionSupDto> suppliers,List<CgBiddingDocumentExaminationDto> biddingDocQualExams,List<CgMeetingConfereeDto> ConfereeOfExperts,Integer biddingDocumentId);

    Integer saveGradeSupSumFirst(List<CgProjectExecutionSupDto> suppliers,List<CgProjectEvalDto> projectEvals,List<CgMeetingConfereeDto> confereeOfExperts,Integer projId,Integer meetingId);

    Integer saveVoteSumFirst(List<CgProjectExecutionSupDto> suppliers,List<CgMeetingConfereeDto> confereeOfExperts,Integer projId,Integer meetingId);

    Integer saveVote(List<CgGradeSupDto> gradeSupDtos);

    /**
     * 二次评审
     * @param saveDto
     * @return
     */
    Integer meetingTwice(@NotNull CgMeetingDetailsDto saveDto);

    Integer saveQualExam(@NotNull CgAuditQualificationDto saveDto);

    Integer saveRespExam(@NotNull CgAuditResponseDto saveDto);

    void saveGradeSup(Integer score, Integer id);

    void saveGradeSupSum(Double sumScore, Integer id);

    void saveGradeSupSumRanking(Integer ranking, Integer id);

    void saveGradeSupSumIsSum(Boolean IsSum, Integer id);

    //根据采购方案id查询
    CgMeetingDetailsDto getDetailsByProjectId(@NotNull Integer id);


}
