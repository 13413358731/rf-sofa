package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

@Schema(description = "专家标签类型对象")
public class CgExpertLabelTypeVo extends BaseVo implements IdentityObject<Integer> {

    public interface Save {
        Integer getId();
        String getName();
    }

    @Schema(description = "ID")
    @NotNull(groups = {CgSupplierLabelVo.Save.class})
    private Integer id;
    @NotNull(groups = {Save.class})
    @Schema(description = "标签类型名称")
    private String name;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
