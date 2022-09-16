package com.realfinance.sofa.common.datascope;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据规则上下文
 */
public class DataRuleContext {

    private StandardEvaluationContext standardEvaluationContext;
    private ExpressionParser expressionParser;
    private ParserContext variableParserContext;
    private Root<?> root;
    private CriteriaBuilder criteriaBuilder;
    private Map<String, Path<?>> pathMap;
    private boolean hasJoin;

    public DataRuleContext(Root<?> root, CriteriaBuilder criteriaBuilder) {
        this(root, criteriaBuilder, null);
    }


    public DataRuleContext(Root<?> root, CriteriaBuilder criteriaBuilder, VariableParser variableParser) {
        this(root, criteriaBuilder, variableParser, null);
    }


    public DataRuleContext(Root<?> root, CriteriaBuilder criteriaBuilder, VariableParser variableParser, Map<String, Path<?>> pathMap) {
        Assert.notNull(root,"Root can not be null");
        Assert.notNull(criteriaBuilder,"CriteriaBuilder can not be null");
        this.root = root;
        this.criteriaBuilder = criteriaBuilder;
        this.pathMap = pathMap == null ? new HashMap<>() : pathMap;
        this.expressionParser = new SpelExpressionParser();
        this.variableParserContext = new TemplateParserContext();
        this.standardEvaluationContext = new StandardEvaluationContext(variableParser);
    }

    public Root<?> getRoot() {
        return root;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }

    public Object parseVariable(String ruleValue) {
        if (ruleValue == null) {
            return null;
        }
        Expression expression = getExpressionParser().parseExpression(ruleValue, variableParserContext);
        return expression.getValue(standardEvaluationContext);
    }

    /**
     * 获取Path对象，已经获取过的会缓存起来避免重复表关联
     * @param pathKey
     * @return
     */
    public Path<?> getPath(String pathKey) {
        Assert.hasText(pathKey, "pathKey 格式不正确");
        Path<?> path = pathMap.get(pathKey);
        if (path != null) {
            return path; // 命中则返回
        }
        String[] s = pathKey.split("\\.");
        if (s.length == 1) {
            path = root.get(s[0]);
        } else {
            Join<Object, Object> join = root.join(s[0], JoinType.LEFT);
            for (int i = 1; i < s.length - 1; i++) {
                join = join.join(s[i],JoinType.LEFT);
            }
            path = join.get(s[s.length - 1]);
            hasJoin = true; // 标记一下使用了表关联
        }
        if (path != null) {
            pathMap.put(pathKey, path);
        }
        return path;
    }

    public boolean hasJoin() {
        return hasJoin;
    }
}
