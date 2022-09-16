package com.realfinance.sofa.flow.facade;

import com.realfinance.sofa.flow.model.HistoricTaskInstanceQueryCriteria;
import com.realfinance.sofa.flow.model.TaskDto;
import com.realfinance.sofa.flow.model.TaskQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

public interface TaskFacade {

    Page<TaskDto> list(TaskQueryCriteria queryCriteria, @NotNull Pageable pageable);

    Page<TaskDto> listHistory(HistoricTaskInstanceQueryCriteria queryCriteria, @NotNull Pageable pageable);

    /**
     * 获取跳转URL
     * @param id 任务ID
     * @return
     */
    String getBizUrl(@NotNull String id);

    /**
     * 签收任务
     * @param id 任务ID
     */
    void claim(@NotNull String id);

    /**
     * 完成任务
     * @param id 任务ID
     */
    void complete(@NotNull String businessCode, @NotNull String id);

}
