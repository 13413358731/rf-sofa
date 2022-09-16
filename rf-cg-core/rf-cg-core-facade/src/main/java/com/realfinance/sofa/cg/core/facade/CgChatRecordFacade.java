package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgChatRecordSaveDto;
import com.realfinance.sofa.cg.core.model.CgMeetingDetailsDto;
import com.realfinance.sofa.cg.core.model.CgMeetingDto;
import com.realfinance.sofa.cg.core.model.CgMeetingQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;

/**
 * 会议聊天记录
 */
public interface CgChatRecordFacade {


    List<CgChatRecordSaveDto> list(Integer meetingId);


    Page<CgMeetingDto> confereelist(CgMeetingQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 保存聊天信息
     * @param meetingId
     * @param message
     * @return
     */
    Integer save(String username,
                 Integer userId,
                 Integer meetingId,
                 String message);

    /**
     * @param id 评审会议Id
     */
    Integer startMeeting(@NotNull Integer id);

    CgMeetingDetailsDto getDetailsById(@NotNull Integer id);
}
