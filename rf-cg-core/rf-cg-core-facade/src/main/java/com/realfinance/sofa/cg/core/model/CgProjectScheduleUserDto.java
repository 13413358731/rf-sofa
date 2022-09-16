package com.realfinance.sofa.cg.core.model;

/**
 * @author hhq
 * @date 2021/6/21 - 19:13
 */
public class CgProjectScheduleUserDto extends BaseDto  {
    public CgProjectScheduleUserDto(){

    }

    public CgProjectScheduleUserDto(Integer id){
        this.id=id;
    }

    private Integer id;

    private Boolean attention;

    private Long v;

//    private CgProjectScheduleDto projectSchedule;

    private String tenantId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAttention() {
        return attention;
    }

    public void setAttention(Boolean attention) {
        this.attention = attention;
    }

//    public CgProjectScheduleDto getProjectSchedule() {
//        return projectSchedule;
//    }
//
//    public void setProjectSchedule(CgProjectScheduleDto projectSchedule) {
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
