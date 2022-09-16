package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.proj.ProjectSchedule;
import com.realfinance.sofa.cg.core.domain.proj.ProjectScheduleUser;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author hhq
 * @date 2021/6/21 - 18:21
 */
public interface ProjectScheduleUserRepository extends JpaRepositoryImplementation<ProjectScheduleUser,Integer> {
}
