package com.group12.lease.common.exception;

import com.group12.lease.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class LeaseException extends RuntimeException {

    private Integer code;    // 错误状态码
    private String message;  // 错误信息
    private ResultCodeEnum resultCodeEnum;

    public LeaseException(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.resultCodeEnum = resultCodeEnum;
    }
}
