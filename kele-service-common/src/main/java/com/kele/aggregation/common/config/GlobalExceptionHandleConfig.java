package com.kele.aggregation.common.config;

import com.kele.aggregation.common.dto.KeleResult;
import com.kele.aggregation.common.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandleConfig {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizRuntimeException.class)
    public KeleResult<Object> handleBizRuntimeException(BizRuntimeException ex) {
        log.warn("[exception record, code={},message=]", ex.getCode(), ex);
        return KeleResult.fail(ex.getCode(), ex.getMessage());
    }
}
