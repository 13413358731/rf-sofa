package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgHoliDayQueryCriteria;
import com.realfinance.sofa.cg.core.model.HoliDayDto;
import com.realfinance.sofa.cg.core.model.HoliDaySaveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CgHoliDayFacade {


    /**
     * 保存
     *
     */
    Integer save(HoliDaySaveDto saveDto);


    /**
     * 列表
     *
     */
    Page<HoliDayDto> list(Pageable pageable, CgHoliDayQueryCriteria queryCriteria);
}
