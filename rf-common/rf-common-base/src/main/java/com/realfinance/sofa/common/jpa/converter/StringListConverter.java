package com.realfinance.sofa.common.jpa.converter;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if (attribute == null) {
            return null;
        }
        if (attribute.isEmpty()) {
            return "";
        }
        return String.join(",",attribute);
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        if (dbData.isEmpty()) {
            return new ArrayList<>();
        }
        return Stream.of(dbData.split(",")).collect(Collectors.toList());
    }
}
