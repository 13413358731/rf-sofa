package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgBusinessReplyDetailsDto extends CgBusinessReplyDto {

    protected List<CgAttachmentDto> attDs;

    /**
     * 供应商上传的文档
     */
    protected List<CgAttachmentDto> attUs;

    /**
     * 报价信息
     */
    protected List<CgBusinessReplyPriceDto> prices;


    public List<CgAttachmentDto> getAttDs() {
        return attDs;
    }

    public void setAttDs(List<CgAttachmentDto> attDs) {
        this.attDs = attDs;
    }

    public List<CgAttachmentDto> getAttUs() {
        return attUs;
    }

    public void setAttUs(List<CgAttachmentDto> attUs) {
        this.attUs = attUs;
    }

    public List<CgBusinessReplyPriceDto> getPrices() {
        return prices;
    }

    public void setPrices(List<CgBusinessReplyPriceDto> prices) {
        this.prices = prices;
    }

}
