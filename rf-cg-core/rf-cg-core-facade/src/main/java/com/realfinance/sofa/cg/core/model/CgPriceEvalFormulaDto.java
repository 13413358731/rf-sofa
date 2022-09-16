package com.realfinance.sofa.cg.core.model;

import java.io.Serializable;

/**
 * 价格计算公式
 */
public class CgPriceEvalFormulaDto implements Serializable {

    private String evalFormulaName;
    private String evalFormula;

    public String getEvalFormulaName() {
        return evalFormulaName;
    }

    public void setEvalFormulaName(String evalFormulaName) {
        this.evalFormulaName = evalFormulaName;
    }

    public String getEvalFormula() {
        return evalFormula;
    }

    public void setEvalFormula(String evalFormula) {
        this.evalFormula = evalFormula;
    }
}
