package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * @author hhq
 * @date 2021/6/21 - 19:30
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "项目进度跟用户关联表对象")
public class CgProjectScheduleUserVo extends BaseVo implements IdentityObject<Integer> {

    private Integer id;

    @Schema(name = "是否关注")
    private Boolean attention;

//    @Schema(name = "项目进度对象")
//    private CgProjectScheduleVo projectSchedule;

    private String tenantId;

    /**
     * 乐观锁版本号
     */
    @Schema(name = "乐观锁版本号")
    private Long v;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }

//    public CgProjectScheduleVo getProjectSchedule() {
//        return projectSchedule;
//    }
//
//    public void setProjectSchedule(CgProjectScheduleVo projectSchedule) {
//        this.projectSchedule = projectSchedule;
//    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }
}
