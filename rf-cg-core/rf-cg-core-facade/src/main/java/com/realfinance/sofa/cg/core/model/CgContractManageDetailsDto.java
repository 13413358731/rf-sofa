package com.realfinance.sofa.cg.core.model;

import java.util.List;

public class CgContractManageDetailsDto extends CgContractManageDto{
    protected List<CgContractAttachmentDto> contractAttachments;

    public List<CgContractAttachmentDto> getContractAttachments() {
        return contractAttachments;
    }

    public void setContractAttachments(List<CgContractAttachmentDto> contractAttachments) {
        this.contractAttachments = contractAttachments;
    }
}
