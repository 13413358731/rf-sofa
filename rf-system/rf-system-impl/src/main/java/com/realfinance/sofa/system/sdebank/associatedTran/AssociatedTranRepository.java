package com.realfinance.sofa.system.sdebank.associatedTran;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface AssociatedTranRepository extends JpaRepositoryImplementation<AssociatedTran, Integer> {
    List<AssociatedTran> findByIdtpAndIdNo(String idtp, String idNo);
}
