package com.kele.aggregation.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class KeleResult<T> implements Serializable {

    private static final long serialVersionUID = 6119633200164279065L;

    public static final int OK_CODE = 0;

    public static final int FAIL_CODE_UNKNOWN = 101;

    public static final String OK_MSG = "ok";

    public static final String FAIL_MSG_UNKNOWN = "未知异常";


    private Integer code;

    private String message;

    private T result;

    public static KeleResult<Object> fail(int code, String message) {
        return new KeleResult<>(code, message, null);
    }

    public static KeleResult<Object> fail() {
        return new KeleResult<>(FAIL_CODE_UNKNOWN, FAIL_MSG_UNKNOWN, null);
    }

    public static <T> KeleResult<T> success(T data, String message) {
        return new KeleResult<T>(OK_CODE, message, data);
    }

    public static <T> KeleResult<T> success(T data) {
        return new KeleResult<T>(OK_CODE, OK_MSG, data);
    }


}
