package com.realfinance.sofa.cg.model;

public interface EditableVo {

    /**
     * 是否可编辑
     * @return
     */
    default Boolean getCanEdit() {
        return true;
    }
}
