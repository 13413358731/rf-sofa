package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgProjectScheduleDetailsSaveDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleUserDto;

import javax.validation.constraints.NotNull;

/**
 * @author hhq
 * @date 2021/6/21 - 18:24
 */
public interface CgProjectScheduleUserFacade {
    Integer save(CgProjectScheduleDetailsSaveDto saveDto);
    CgProjectScheduleUserDto getById(@NotNull Integer id);
}
