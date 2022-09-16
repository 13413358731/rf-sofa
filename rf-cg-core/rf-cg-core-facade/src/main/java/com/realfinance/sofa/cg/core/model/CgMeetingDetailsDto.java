package com.realfinance.sofa.cg.core.model;

import java.util.List;

public class CgMeetingDetailsDto extends CgMeetingDto {

    private List<CgMeetingConfereeDto> conferees;

    public List<CgMeetingConfereeDto> getConferees() {
        return conferees;
    }

    public void setConferees(List<CgMeetingConfereeDto> conferees) {
        this.conferees = conferees;
    }
}
