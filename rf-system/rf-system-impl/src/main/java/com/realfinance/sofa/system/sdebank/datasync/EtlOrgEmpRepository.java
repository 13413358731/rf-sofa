package com.realfinance.sofa.system.sdebank.datasync;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface EtlOrgEmpRepository extends JpaRepositoryImplementation<EtlOrgEmp, Integer> {

    List<EtlOrgEmp> findByIsmain(String y);
}
