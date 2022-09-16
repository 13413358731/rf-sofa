package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupplierBlacklistDetailsDto extends CgSupplierBlacklistDto {
    /**
     * 附件
     */
    private List<CgAttachmentDto> attachments;

    public List<CgAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttachmentDto> attachments) {
        this.attachments = attachments;
    }
}
