package com.realfinance.sofa.common.datascope;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.util.Assert;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public enum RuleConditions {
    SQL {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.isInstanceOf(String.class,ruleValue);
            Assert.hasText((String) ruleValue,"RuleValue can not be blank");
            return new CustomSqlPredicate(ruleValue.toString(), dataRuleContext);
        }
    },
    EQUAL {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Assert.notNull(ruleValue,"RuleValue can not be null");
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            return dataRuleContext.getCriteriaBuilder().equal(path, ruleValue);
        }
    },
    NOT_EQUAL {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Assert.notNull(ruleValue,"RuleValue can not be null");
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            return dataRuleContext.getCriteriaBuilder().notEqual(path, ruleValue);
        }
    },
    GT {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Number value;
            if (ruleValue instanceof Number) {
                value = (Number) ruleValue;
            } else if (ruleValue instanceof String){
                value = NumberUtils.createNumber((String) ruleValue);
            } else {
                throw new IllegalArgumentException("RuleValue is not a digits");
            }
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            return dataRuleContext.getCriteriaBuilder().gt(path.as(Number.class), value);
        }
    },
    GE {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Number value;
            if (ruleValue instanceof Number) {
                value = (Number) ruleValue;
            } else if (ruleValue instanceof String){
                value = NumberUtils.createNumber((String) ruleValue);
            } else {
                throw new IllegalArgumentException("RuleValue is not a digits");
            }
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            return dataRuleContext.getCriteriaBuilder().ge(path.as(Number.class), value);
        }
    },
    LT {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Number value;
            if (ruleValue instanceof Number) {
                value = (Number) ruleValue;
            } else if (ruleValue instanceof String){
                value = NumberUtils.createNumber((String) ruleValue);
            } else {
                throw new IllegalArgumentException("RuleValue is not a digits");
            }
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            return dataRuleContext.getCriteriaBuilder().lt(path.as(Number.class), value);
        }
    },
    LE {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Number value;
            if (ruleValue instanceof Number) {
                value = (Number) ruleValue;
            } else if (ruleValue instanceof String){
                value = NumberUtils.createNumber((String) ruleValue);
            } else {
                throw new IllegalArgumentException("RuleValue is not a digits");
            }
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            return dataRuleContext.getCriteriaBuilder().le(path.as(Number.class), value);
        }
    },
    LIKE {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Assert.isInstanceOf(String.class,ruleValue);
            Assert.hasText((String) ruleValue,"RuleValue can not be blank");
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            return dataRuleContext.getCriteriaBuilder().like(path.as(String.class), ruleValue.toString());
        }
    },
    IN {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Assert.isInstanceOf(String.class,ruleValue);
            Assert.hasText((String) ruleValue,"RuleValue can not be blank");
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            Object[] values = ruleValue.toString().split(",");
            return path.in((values));
        }
    },
    NOT_IN {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Assert.isInstanceOf(String.class,ruleValue);
            Assert.hasText((String) ruleValue,"RuleValue can not be blank");
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            Object[] values = ruleValue.toString().split(",");
            return dataRuleContext.getCriteriaBuilder().not(path.in(values));
        }
    },
    IS_NULL {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            return dataRuleContext.getCriteriaBuilder().isNull(path);
        }
    },
    IS_NOT_NULL {
        @Override
        public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext) {
            Assert.hasText(ruleAttribute,"RuleAttribute can not be blank");
            Path<?> path = dataRuleContext.getPath(ruleAttributeToPathKey(ruleAttribute));
            return dataRuleContext.getCriteriaBuilder().isNotNull(path);
        }
    };

    abstract public Predicate toPredicate(String ruleAttribute, Object ruleValue, DataRuleContext dataRuleContext);

    protected String ruleAttributeToPathKey(String ruleAttribute) {
        return ruleAttribute;
    }
}
