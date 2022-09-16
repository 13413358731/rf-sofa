package com.realfinance.sofa.common.datascope;

import com.realfinance.sofa.system.model.MenuDataRuleSmallDto;
import com.realfinance.sofa.system.model.MenuDataRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

/**
 * Jpa查询工具类
 */
public class JpaQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(JpaQueryHelper.class);

    protected VariableParser variableParser = new VariableParser();

    /**
     * 查询数据规则 返回Specification
     * @param <T>
     * @return
     */
    public <T> Specification<T> dataRuleSpecification() {
        return (Specification<T>) (root, query, criteriaBuilder) -> query.where(
                menuDataRulePredicate(root,query,criteriaBuilder),
                tenantPredicate(root,query,criteriaBuilder)).getRestriction();
    }

    /**
     * 租户（法人）条件谓语
     * 约定实体中字段名为 tenantId
     * @param root
     * @param query
     * @param criteriaBuilder
     * @param <T>
     * @return
     */
    public <T> Predicate tenantPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        boolean skip = DataScopeUtils.loadSkipValidateTenantId();
        if (skip) {
            if (log.isTraceEnabled()) {
                log.trace("Skip tenant predicate");
            }
            return criteriaBuilder.and();
        } else {
            String tenantId = DataScopeUtils.loadTenantId();
            try {
                // 检查是否有tenantId，如果没有则忽略
                root.getModel().getAttribute("tenantId");
            } catch (Exception ignore) {
                if (log.isWarnEnabled()) {
                    log.warn("{} 没找到tenantId属性",root.getModel().getName());
                }
                return criteriaBuilder.and();
            }
            return criteriaBuilder.equal(root.get("tenantId"), tenantId);
        }
    }

    /**
     * 查询数据规则 返回Predicate
     * 向系统服务查询每个角色在当前菜单中的数据规则集合
     * 不同角色间的数据规则集合用OR连接
     * 每个角色的数据规则集合用AND连接
     * @param root
     * @param query
     * @param criteriaBuilder
     * @param <T>
     * @return
     */
    public <T> Predicate menuDataRulePredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Optional<MenuDataRules> menuDataRules = DataScopeUtils.loadMenuDataRules();
        if (log.isTraceEnabled()) {
            log.trace("读取数据权限规则：{}",menuDataRules);
        }
        // menuDataRules为null表示没有安装数据规则
        if (!menuDataRules.map(MenuDataRules::getHasMenuDataRule).orElse(false)) {
            // 没安装数据规则或者菜单下没有数据规则时直接返回
            return criteriaBuilder.and();
        }
        Collection<MenuDataRules.RoleRules> roleRulesCollection = menuDataRules.get().getRoleRulesCollection();
        if (roleRulesCollection == null || roleRulesCollection.isEmpty()) {
            return criteriaBuilder.and();
        }
        DataRuleContext dataRuleContext = new DataRuleContext(root, criteriaBuilder, variableParser);
        // 不同角色之间的数据规则条件用OR连接， 角色内数据规则条件用AND连接
        Predicate[] orPredicates = roleRulesCollection.stream()
                .map(MenuDataRules.RoleRules::getRules)
                .sorted(Comparator.comparing(Collection::size))
                .takeWhile(e -> !e.isEmpty())
                .map(e -> criteriaBuilder.and(e.stream()
                        .map(menuDataRule -> parseMenuDataRule(menuDataRule, dataRuleContext))
                        .toArray(Predicate[]::new)))
                .toArray(Predicate[]::new);
        // 如果有JOIN，则distinct，不然分页是可能出现问题
        if (dataRuleContext.hasJoin()) {
            query.distinct(true);
        }
        return criteriaBuilder.or(orPredicates);
    }

    /**
     * 解析数据规则
     * @param menuDataRule
     * @param dataRuleContext
     * @return
     */
    protected Predicate parseMenuDataRule(MenuDataRuleSmallDto menuDataRule,
                                          DataRuleContext dataRuleContext) {
        if (menuDataRule == null) {
            log.error("数据规则为null，跳过此规则");
            return dataRuleContext.getCriteriaBuilder().and();
        }

        RuleConditions ruleConditions;
        try {
            ruleConditions = Enum.valueOf(RuleConditions.class, menuDataRule.getRuleConditions());
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("不支持的规则条件 {}",menuDataRule.getRuleConditions());
            }
            return dataRuleContext.getCriteriaBuilder().and();
        }

        String ruleAttribute = menuDataRule.getRuleAttribute();
        Object parsedRuleValue = dataRuleContext.parseVariable(menuDataRule.getRuleValue());
        try {
            return ruleConditions.toPredicate(ruleAttribute, parsedRuleValue, dataRuleContext);
        } catch (Exception e) {
            log.error("规则配置不正确，跳过此规则: {}",menuDataRule);
            // TODO: 2020/11/29 未确定这里是该抛出异常还是忽略此规则，暂定忽略规则
            return dataRuleContext.getCriteriaBuilder().and();
        }
    }

    public void setVariableParser(VariableParser variableParser) {
        this.variableParser = variableParser;
    }
}
