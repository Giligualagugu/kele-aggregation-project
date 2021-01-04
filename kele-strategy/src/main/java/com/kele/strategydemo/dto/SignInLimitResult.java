package com.kele.strategydemo.dto;

import lombok.Data;

@Data
public class SignInLimitResult {

    boolean limited;

    private int limitMinutes;

}
