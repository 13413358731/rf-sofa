package com.realfinance.sofa.common.jpa.converter;

import java.util.List;
import java.util.stream.Collectors;

public class DistinctStringListConverter extends StringListConverter {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute != null && attribute.size() > 1) {
            attribute = attribute.stream().distinct().collect(Collectors.toList());
        }
        return super.convertToDatabaseColumn(attribute);
    }
}
