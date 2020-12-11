package com.kele.jpa.demo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum implements CommonEnumInterface {

    MALE(1, "男"),
    FEMALE(2, "女"),
    UN_KNWON(3, "未知");

    private final int code;
    private final String msg;

    public static GenderEnum parseGenderEnum(int code) {
        for (GenderEnum value : GenderEnum.values()) {
            if (value.code == code) {
                return value;
            }
        }
        return UN_KNWON;
    }

}
