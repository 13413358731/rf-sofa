package com.realfinance.sofa.cg.core.service.evalformula;

public class F2 implements EvalFormula {

    @Override
    public String getName() {
        return "计算方法2";
    }

    @Override
    public String getFormula() {
        return "单项得分=分值*[1-|评标价/基准价-1|]；总分=∑单项得分*权重";
    }
}
