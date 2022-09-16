package com.realfinance.sofa.cg.sup.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

//@Entity
//@Table(name = "CG_SUP_PARTICIPATE_PROJECT")
public class SupplierParticipateProject {
    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String projectId;

    private String projectName;


}
