package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.meeting.Meeting;
import com.realfinance.sofa.cg.core.domain.meeting.MeetingChatRecord;
import com.realfinance.sofa.cg.core.facade.CgChatRecordFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.*;
import com.realfinance.sofa.cg.core.service.mapstruct.*;
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
@SofaService(interfaceType = CgChatRecordFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgChatRecordImpl implements CgChatRecordFacade {
    private static final Logger log = LoggerFactory.getLogger(CgChatRecordImpl.class);

    private final ChatRecordRepository chatRecordRepository;
    private final MeetingRepository meetingRepository;
    private final JpaQueryHelper jpaQueryHelper;
    private final ChatRecordMapper chatRecordMapper;

    public CgChatRecordImpl(ChatRecordRepository chatRecordRepository, MeetingRepository meetingRepository, JpaQueryHelper jpaQueryHelper, ChatRecordMapper chatRecordMapper) {
        this.chatRecordRepository = chatRecordRepository;
        this.meetingRepository = meetingRepository;
        this.jpaQueryHelper = jpaQueryHelper;
        this.chatRecordMapper = chatRecordMapper;
    }

    @Override
    public List<CgChatRecordSaveDto> list(Integer meetingId) {
        CgChatRecordQueryCriteria queryCriteria = new CgChatRecordQueryCriteria();
        queryCriteria.setMeetingId(meetingId);
        List<MeetingChatRecord> result = chatRecordRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()));
        return result.stream().map(chatRecordMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Page<CgMeetingDto> confereelist(CgMeetingQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(String username, Integer userId, Integer meetingId, String message) {
        MeetingChatRecord meetingChatRecord = new MeetingChatRecord();
        meetingChatRecord.setContent(message);
        meetingChatRecord.setMeeting(getMeeting(meetingId));
        meetingChatRecord.setSenderName(username);
        meetingChatRecord.setSenderUserId(userId);
        meetingChatRecord.setSendTime(LocalDateTime.now());
//        if (saveDto.getId() == null) { // 新增
//            throw businessException("不允许新增");
//        } else { // 修改
//            Project entity = getProject(saveDto.getId());
//            ProjectCategory projectCategory = entity.getProjectCategory();
//            project = projectDetailsSaveMapper.updateEntity(entity,saveDto);
//            projectCategoryChanged = projectCategory != project.getProjectCategory();
//        }
////        preSave(project);
//        if (projectCategoryChanged) {
//            // 如果项目类别发生改变，生成项目编码
//            generateProjectNo(project);
//        }
        try {
            MeetingChatRecord saved = chatRecordRepository.saveAndFlush(meetingChatRecord);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败",e);
            }
            throw businessException("保存失败");
        }
    }

    @Override
    public Integer startMeeting(@NotNull Integer id) {
        return null;
    }

    @Override
    public CgMeetingDetailsDto getDetailsById(@NotNull Integer id) {
        return null;
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
}
