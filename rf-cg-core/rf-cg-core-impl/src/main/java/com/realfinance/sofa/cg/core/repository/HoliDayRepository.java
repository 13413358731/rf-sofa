package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.HoliDay;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface HoliDayRepository extends JpaRepositoryImplementation<HoliDay, Integer> {


    HoliDay findByHolidayDate(String holiday);
}
