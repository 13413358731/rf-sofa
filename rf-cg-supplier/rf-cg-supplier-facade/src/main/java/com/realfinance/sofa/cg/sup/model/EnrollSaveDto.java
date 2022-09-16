package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

public class EnrollSaveDto implements Serializable {

    private Integer id;

    private  Integer supplierId;

    private  Integer  solicitationId;

    private String supplierName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(Integer solicitationId) {
        this.solicitationId = solicitationId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
