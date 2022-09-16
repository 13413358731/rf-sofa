package com.realfinance.sofa.cg.core.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 已指派专家
 */
@Entity
@Table(name = "CG_CORE_DELEGATEMEMBER")
public class DelegateMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *参会专家姓名
     */
    @Column(nullable = false)
    private String name;

    /**
     *参会专家编码
     */
    @Column(nullable = false)
    private String expertCode;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpertCode() {
        return expertCode;
    }

    public void setExpertCode(String expertCode) {
        this.expertCode = expertCode;
    }
}
