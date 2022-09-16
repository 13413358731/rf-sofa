package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "评委打分转换类")
public class CgGradeSupConvertVo extends BaseVo implements IdentityObject<Integer> {
    private Integer id;

    private List<Object> gradeItems;

    /**
     * 评委打分， 动态表单
     */
    @Schema(description = "打分信息")
    private Object gradeInfo;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public List<Object> getGradeItems() {
        return gradeItems;
    }

    public void setGradeItems(List<Object> gradeItems) {
        this.gradeItems = gradeItems;
    }

    public Object getGradeInfo() {
        return gradeInfo;
    }

    public void setGradeInfo(Object gradeInfo) {
        this.gradeInfo = gradeInfo;
    }
}
