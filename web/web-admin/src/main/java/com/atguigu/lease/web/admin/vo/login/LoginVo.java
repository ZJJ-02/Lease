package com.atguigu.lease.web.admin.vo.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Schema(description = "后台管理系统登录信息")
public class LoginVo {

    @Schema(description="用户名")
    private String username;

    @Schema(description="密码")
    private String password;

    @Schema(description="验证码key")
    private String captchaKey;

    @Schema(description="验证码code")
    @NotEmpty(message = "验证码不能为空")
    private String captchaCode;
}
