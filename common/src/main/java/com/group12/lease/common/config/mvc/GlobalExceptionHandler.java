package com.group12.lease.common.config.mvc;

import com.group12.lease.common.exception.LeaseException;
import com.group12.lease.common.result.Result;
import com.group12.lease.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LeaseException.class)
    public Result leaseException(LeaseException e) {
        e.printStackTrace();
        return Result.build(null, e.getResultCodeEnum());
    }

    @ExceptionHandler(Exception.class)
    public Result otherException(Exception e) {
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.FAIL);
    }
}
