package com.group12.lease.web.app.vo.user;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "APP端登录实体")
public class LoginVo {

    @Schema(description = "手机号码")
    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @Schema(description = "短信验证码")
    @NotEmpty(message = "验证码不能为空")
    private String code;
}
