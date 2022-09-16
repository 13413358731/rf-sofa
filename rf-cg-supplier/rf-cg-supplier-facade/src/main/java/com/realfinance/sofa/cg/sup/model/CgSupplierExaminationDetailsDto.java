package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupplierExaminationDetailsDto extends CgSupplierExaminationDto {

    private List<CgSupplierExaminationContactsDto> contacts;
    private List<CgAttachmentDto> attachments;
    private List<CgSupplierExaminationQuallityAuthDto> qualityAuths;
    private List<CgSupplierExaminationCreditDto> credits;
    public List<CgSupplierExaminationContactsDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<CgSupplierExaminationContactsDto> contacts) {
        this.contacts = contacts;
    }

    public List<CgAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public List<CgSupplierExaminationQuallityAuthDto> getQualityAuths() {
        return qualityAuths;
    }

    public void setQualityAuths(List<CgSupplierExaminationQuallityAuthDto> qualityAuths) {
        this.qualityAuths = qualityAuths;
    }

    public List<CgSupplierExaminationCreditDto> getCredits() {
        return credits;
    }

    public void setCredits(List<CgSupplierExaminationCreditDto> credits) {
        this.credits = credits;
    }
}
