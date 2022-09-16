package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgProjectScheduleDto;
import com.realfinance.sofa.cg.core.model.CgProjectScheduleQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;

/**
 * @author hhq
 * @date 2021/6/21 - 18:24
 */
public interface CgProjectScheduleFacade {
    Page<CgProjectScheduleDto> list(CgProjectScheduleQueryCriteria queryCriteria,@NotNull Pageable pageable);
}
