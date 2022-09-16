package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgBusinessProjectDetailsDto extends CgBusinessProjectDto {
    /**
     * 商务应答
     */
    private List<CgBusinessReplyDto> replies;

    public CgBusinessProjectDetailsDto(Integer id, List<CgBusinessReplyDto> replies) {
        super(id);
        this.replies = replies;
    }

    public List<CgBusinessReplyDto> getReplies() {
        return replies;
    }

    public void setReplies(List<CgBusinessReplyDto> replies) {
        this.replies = replies;
    }
}
