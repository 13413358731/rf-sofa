package com.realfinance.sofa.flow.flowable.form;

import com.realfinance.sofa.flow.util.UserIdList;
import org.apache.commons.lang3.StringUtils;
import org.flowable.common.engine.api.FlowableIllegalArgumentException;
import org.flowable.engine.form.AbstractFormType;

import java.util.Arrays;
import java.util.List;

/**
 * 用户列表表单类型
 */
public class UsersFormType extends AbstractFormType {

    private static final long serialVersionUID = 1L;

    protected List<String> userScope;
    protected List<String> groupScope;
    protected Integer maxSize;
    protected Integer minSize;

    public UsersFormType(List<String> userScope, List<String> groupScope, Integer maxSize, Integer minSize) {
        this.userScope = userScope;
        this.groupScope = groupScope;
        if (maxSize != null && maxSize > 0) {
            this.maxSize = maxSize;
        }
        if (minSize != null && minSize > 0) {
            this.minSize = minSize;
        }
    }

    @Override
    public Object getInformation(String key) {
        if ("userScope".equals(key)) {
            return userScope;
        } else if ("groupScope".equals(key)) {
            return groupScope;
        } else if ("maxSize".equals(key)) {
            return maxSize;
        } else if ("minSize".equals(key)) {
            return minSize;
        }
        return null;
    }

    @Override
    public Object convertFormValueToModelValue(String propertyValue) {
        if (StringUtils.isEmpty(propertyValue)) {
            return new UserIdList();
        }
        String[] split = propertyValue.split("[\\s]*,[\\s]*");
        if (maxSize != null && split.length > maxSize) {
            throw new FlowableIllegalArgumentException("人数超过最大值");
        }
        if (minSize != null && split.length < minSize) {
            throw new FlowableIllegalArgumentException("人数少于最小值");
        }
        return new UserIdList(Arrays.asList(split));
    }

    @Override
    public String convertModelValueToFormValue(Object modelValue) {
        if (modelValue == null) {
            return StringUtils.EMPTY;
        }
        if (modelValue instanceof Iterable) {
            @SuppressWarnings("unchecked")
            String result = String.join(",", (Iterable<? extends CharSequence>) modelValue);
            return result;
        } else if (modelValue instanceof String) {
            return (String) modelValue;
        } else {
            throw new FlowableIllegalArgumentException("不支持类型");
        }
    }

    @Override
    public String getName() {
        return "users";
    }
}
