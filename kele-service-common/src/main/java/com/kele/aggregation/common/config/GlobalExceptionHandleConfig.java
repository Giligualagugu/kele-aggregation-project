package com.kele.aggregation.common.config;

import com.kele.aggregation.common.dto.KeleResult;
import com.kele.aggregation.common.exception.BizRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
/**
 * 需自行注册处理 @RestControllerAdvice
 */
public class GlobalExceptionHandleConfig {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizRuntimeException.class)
    public KeleResult<Object> handleBizRuntimeException(BizRuntimeException ex) {
        log.warn("[exception record, code={},message=]", ex.getCode(), ex);
        return KeleResult.fail(ex.getCode(), ex.getMessage());
    }
}
