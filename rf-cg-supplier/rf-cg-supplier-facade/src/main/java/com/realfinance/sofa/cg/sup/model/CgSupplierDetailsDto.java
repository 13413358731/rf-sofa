package com.realfinance.sofa.cg.sup.model;

import java.util.List;
import java.util.Set;

public class CgSupplierDetailsDto extends CgSupplierDto {

    public CgSupplierDetailsDto() {
    }

    public CgSupplierDetailsDto(Integer id) {
        super(id);
    }

    protected List<CgSupplierContactsDto> contacts;

    protected List<CgAttachmentDto> attachments;

    protected Set<CgSupplierLabelSmallDto> supplierLabels;

    protected List<CgSupplierCreditDto> credits;

    protected List<CgSupplierQualityAuthDto> qualityAuths;

    public List<CgSupplierContactsDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<CgSupplierContactsDto> contacts) {
        this.contacts = contacts;
    }

    public List<CgAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public Set<CgSupplierLabelSmallDto> getSupplierLabels() {
        return supplierLabels;
    }

    public void setSupplierLabels(Set<CgSupplierLabelSmallDto> supplierLabels) {
        this.supplierLabels = supplierLabels;
    }

    public List<CgSupplierCreditDto> getCredits() {
        return credits;
    }

    public void setCredits(List<CgSupplierCreditDto> credits) {
        this.credits = credits;
    }

    public List<CgSupplierQualityAuthDto> getQualityAuths() {
        return qualityAuths;
    }

    public void setQualityAuths(List<CgSupplierQualityAuthDto> qualityAuths) {
        this.qualityAuths = qualityAuths;
    }
}
