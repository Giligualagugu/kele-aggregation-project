package com.kele.strategydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInLimitResult {

    boolean limited;

    private int limitMinutes;

}
