package com.realfinance.sofa.flow.repository;

import com.realfinance.sofa.flow.domain.Biz;
import com.realfinance.sofa.flow.domain.BizModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import javax.persistence.LockModeType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BizModelRepository extends JpaRepositoryImplementation<BizModel, Integer> {

    Page<BizModel> findByDepartmentId(Integer departmentId, Pageable pageable);

    Optional<BizModel> findByBizAndDepartmentIdAndDeploymentIdNotNull(Biz biz, Integer departmentId);

    List<BizModel> findByBizAndDepartmentIdInAndDeploymentIdNotNull(Biz biz, Collection<Integer> departmentIds);

    Optional<BizModel> findByDeploymentId(String deploymentId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT bm FROM BizModel bm WHERE bm.id = :id")
    Optional<BizModel> lockBizModel(Integer id);
}
