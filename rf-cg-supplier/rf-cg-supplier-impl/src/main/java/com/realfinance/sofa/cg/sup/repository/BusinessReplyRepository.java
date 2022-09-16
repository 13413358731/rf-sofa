package com.realfinance.sofa.cg.sup.repository;

import com.realfinance.sofa.cg.sup.domain.BusinessProject;
import com.realfinance.sofa.cg.sup.domain.BusinessReply;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface BusinessReplyRepository extends JpaRepositoryImplementation<BusinessReply, Integer> {

    boolean existsByProjectAndReleaseIdAndReplyType(BusinessProject businessProject, String releaseId, String replyType);

    List<BusinessReply> findByReleaseIdAndReplyType(String releaseId, String replyType);
}
