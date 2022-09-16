package com.realfinance.sofa.common.datascope;

import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.QueryStructure;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;
import org.springframework.expression.Expression;
import org.springframework.expression.common.CompositeStringExpression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * 自定义SQL谓语
 */
public class CustomSqlPredicate extends AbstractSimplePredicate implements Serializable {

    /**
     * 读取Path模板，SQL中通过$entity.xxx$获取Path
     */
    private static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext("$entity.", "$");

    private DataRuleContext dataRuleContext;
    private Expression expression;

    public CustomSqlPredicate(String sql, DataRuleContext dataRuleContext) {
        super((CriteriaBuilderImpl) dataRuleContext.getCriteriaBuilder());
        Assert.notNull(sql,"Sql can not be null");
        Assert.notNull(dataRuleContext,"DataRuleContext can not be null");
        this.dataRuleContext = dataRuleContext;
        this.expression = dataRuleContext.getExpressionParser().parseExpression(sql, TEMPLATE_PARSER_CONTEXT);
        initPath(expression);
    }


    @Override
    public void registerParameters(ParameterRegistry registry) {
        registerParameters(registry,this.expression);
    }

    @Override
    public String render(boolean isNegated, RenderingContext renderingContext) {
        if (isNegated) {
            throw new IllegalArgumentException("不支持否定");
        }
        StringBuilder sb = new StringBuilder();
        render(sb, renderingContext, this.expression);
        return sb.toString();
    }

    protected void render(StringBuilder sb, RenderingContext renderingContext, Expression expression) {
        if (expression instanceof CompositeStringExpression) {
            for (Expression e : ((CompositeStringExpression) this.expression).getExpressions()) {
                render(sb,renderingContext,e);
            }
        } else if (expression instanceof SpelExpression) {
            sb.append(((Renderable) dataRuleContext.getPath(expression.getExpressionString())).render(renderingContext));
        } else {
            sb.append(expression.getValue(this, String.class));
        }
    }

    protected void registerParameters(ParameterRegistry registry, Expression expression) {
        if (expression instanceof CompositeStringExpression) {
            for (Expression e : ((CompositeStringExpression) this.expression).getExpressions()) {
                registerParameters(registry,e);
            }
        } else if (expression instanceof SpelExpression) {
            Helper.possibleParameter(dataRuleContext.getPath(expression.getExpressionString()), registry);
        }
    }

    /**
     * 如果需要表关联，要在
     * {@link QueryStructure#render(StringBuilder, RenderingContext)}
     * 中的renderFromClause之前获取JOIN对象
     */
    protected void initPath(Expression expression) {
        if (expression instanceof CompositeStringExpression) {
            for (Expression e : ((CompositeStringExpression) this.expression).getExpressions()) {
                initPath(e);
            }
        } else if (expression instanceof SpelExpression) {
            dataRuleContext.getPath(expression.getExpressionString());
        }
        System.out.println(expression);
    }
}
