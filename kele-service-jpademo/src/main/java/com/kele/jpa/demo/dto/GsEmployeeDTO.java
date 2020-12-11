package com.kele.jpa.demo.dto;

import com.kele.jpa.demo.enums.GenderEnum;
import lombok.Data;

@Data
public class GsEmployeeDTO {

    private Integer belongGsId;

    private String username;

    private Integer age;

    private GenderEnum sex = GenderEnum.UN_KNWON;

}
