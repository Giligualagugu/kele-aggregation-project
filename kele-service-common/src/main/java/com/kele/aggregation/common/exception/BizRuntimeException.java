package com.kele.aggregation.common.exception;

import lombok.Data;

@Data
public class BizRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 8905531579475605587L;
    private int code = 101;

    public BizRuntimeException() {
    }

    public BizRuntimeException(String message, Throwable e) {
        super(message);
        this.setStackTrace(e.getStackTrace());
    }

    public BizRuntimeException(String message) {
        super(message);
    }

    public BizRuntimeException(int errorCode, String message) {
        super(message);
        this.code = errorCode;
    }

}
