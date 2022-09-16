package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 资质授权
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_QUALITYAUTH")
public class QualityAuth implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 资质名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 生效日期
     */
    @Column(nullable = false)
    private LocalDate validTime;

    /**
     * 失效日期
     */
    @Column(nullable = false)
    private LocalDate invalidTime;

    /**
     * 发证单位
     */
    @Column(nullable = false)
    private String authorizeUnit;

    @ManyToOne
    @JoinColumn(name = "supplier_id", updatable = false)
    private Supplier supplier;

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

    public LocalDate getValidTime() {
        return validTime;
    }

    public void setValidTime(LocalDate validTime) {
        this.validTime = validTime;
    }

    public LocalDate getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(LocalDate invalidTime) {
        this.invalidTime = invalidTime;
    }

    public String getAuthorizeUnit() {
        return authorizeUnit;
    }

    public void setAuthorizeUnit(String authorizeUnit) {
        this.authorizeUnit = authorizeUnit;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}
