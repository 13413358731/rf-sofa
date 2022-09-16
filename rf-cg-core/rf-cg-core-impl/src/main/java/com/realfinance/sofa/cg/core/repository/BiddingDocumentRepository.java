package com.realfinance.sofa.cg.core.repository;

import com.realfinance.sofa.cg.core.domain.exec.bid.BiddingDocument;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;

public interface BiddingDocumentRepository extends JpaRepositoryImplementation<BiddingDocument, Integer> {

    Optional<BiddingDocument> findByProjectExecution(ProjectExecution projectExecution);
}
