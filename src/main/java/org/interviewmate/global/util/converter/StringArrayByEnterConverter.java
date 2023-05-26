package org.interviewmate.global.util.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class StringArrayByEnterConverter implements AttributeConverter<List<String>, String> {
    private static final String SPLIT_CHAR = "\n";

    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return stringList.stream().collect(Collectors.joining(SPLIT_CHAR));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(SPLIT_CHAR)).collect(Collectors.toList());
    }
}
