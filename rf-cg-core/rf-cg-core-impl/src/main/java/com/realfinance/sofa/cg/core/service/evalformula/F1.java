package com.realfinance.sofa.cg.core.service.evalformula;

public class F1 implements EvalFormula {

    @Override
    public String getName() {
        return "计算方法1";
    }

    @Override
    public String getFormula() {
        return "单项得分=分值*基准价/评标价；总分=∑单项得分*权重";
    }
}
