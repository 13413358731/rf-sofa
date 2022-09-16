package com.realfinance.sofa.system.sdebank.datasync;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.List;

public interface EtlEmpRepository extends JpaRepositoryImplementation<EtlEmp, Integer> {

    List<EtlEmp> findByEmpcodeIn(List<String> empcodeList);
}
