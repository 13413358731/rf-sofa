package com.realfinance.sofa.cg.core.service.evalformula;

public class F4 implements EvalFormula {

    @Override
    public String getName() {
        return "计算方法4";
    }

    @Override
    public String getFormula() {
        return "单项得分=分值*[1-A*|1-评标价/基准价|]；总分=∑单项得分*权重;其中A为价格调整系数，当投标报价低于本次招标基准价时，A=0.5；当投标报价等于或高于本次招标基准价时，A=1";
    }
}
