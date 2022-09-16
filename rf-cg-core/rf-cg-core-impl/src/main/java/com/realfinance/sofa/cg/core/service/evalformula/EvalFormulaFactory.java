package com.realfinance.sofa.cg.core.service.evalformula;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EvalFormulaFactory {

    private static final Map<String,EvalFormula> STORE = new ConcurrentHashMap<>();

    static {
        addEvalFormula(new F1());
        addEvalFormula(new F2());
        addEvalFormula(new F3());
        addEvalFormula(new F4());
    }

    public static void addEvalFormula(EvalFormula formula) {
        STORE.put(formula.getName(),formula);
    }

    public static void removeEvalFormula(String name) {
        STORE.remove(name);
    }

    public static EvalFormula getEvalFormula(String name) {
        if (STORE.isEmpty()) {
            throw new EvalFormulaNotFoundException("可用计算方法数为0");
        }
        EvalFormula evalFormula = STORE.get(name);
        if (evalFormula == null) {
            throw new EvalFormulaNotFoundException("找不到计算方法：" + name);
        }
        return evalFormula;
    }

    public static Collection<EvalFormula> listEvalFormula() {
        return Collections.unmodifiableCollection(STORE.values());
    }
}
