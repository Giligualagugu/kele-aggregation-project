package com.kele.jpa.demo.enums;

import javax.persistence.AttributeConverter;

/**
 * javabean 和数据库类型的 转换器, 此处用于处理枚举;
 */
public class GenderEnumConverter implements AttributeConverter<GenderEnum, Integer> {

    @Override
    public Integer convertToDatabaseColumn(GenderEnum attribute) {
        return attribute.getCode();
    }

    @Override
    public GenderEnum convertToEntityAttribute(Integer dbData) {
        return GenderEnum.parseGenderEnum(dbData);
    }
}
