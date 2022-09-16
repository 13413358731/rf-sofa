package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "公告频道对象")
public class AnnouncementChannelVo extends BaseVo  {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "频道编号")
    private  Integer channelNo;

    @Schema(description = "频道名称")
    private  String   channelName;

    @Schema(description = "频道类型")
    private  String  channelType;

    @Schema(description = "备注")
    private  String  remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(Integer channelNo) {
        this.channelNo = channelNo;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
