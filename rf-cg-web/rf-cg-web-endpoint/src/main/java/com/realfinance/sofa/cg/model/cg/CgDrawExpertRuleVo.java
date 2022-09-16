package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "专家对象")
public class CgDrawExpertRuleVo extends BaseVo implements IdentityObject<Integer> {

    public interface Save {
    }

    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "抽取部门Id")
    protected DepartmentVo expertDeptId;

    @Schema(description = "抽取标签Id")
    protected CgExpertLabelVo expertLabelId;

    @Schema(description = "抽取人数")
    protected Integer expertCount;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public DepartmentVo getExpertDeptId() {
        return expertDeptId;
    }

    public void setExpertDeptId(DepartmentVo expertDeptId) {
        this.expertDeptId = expertDeptId;
    }

    public CgExpertLabelVo getExpertLabelId() {
        return expertLabelId;
    }

    public void setExpertLabelId(CgExpertLabelVo expertLabelId) {
        this.expertLabelId = expertLabelId;
    }

    public Integer getExpertCount() {
        return expertCount;
    }

    public void setExpertCount(Integer expertCount) {
        this.expertCount = expertCount;
    }
}
