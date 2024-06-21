package com.group12.lease.web.admin.controller.user;


import com.group12.lease.common.result.Result;
import com.group12.lease.model.entity.UserInfo;
import com.group12.lease.model.enums.BaseStatus;
import com.group12.lease.web.admin.service.UserInfoService;
import com.group12.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户信息管理")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Operation(summary = "分页查询用户信息")
    @GetMapping("page")
    public Result<IPage<UserInfo>> pageUserInfo(@RequestParam long current, @RequestParam long size, UserInfoQueryVo queryVo) {
        IPage<UserInfo> page = new Page<>(current, size);
        IPage<UserInfo> result = userInfoService.page(page, new LambdaQueryWrapper<UserInfo>()
                .like(StringUtils.hasText(queryVo.getPhone()), UserInfo::getPhone, queryVo.getPhone())
                .eq(!ObjectUtils.isEmpty(queryVo.getStatus()), UserInfo::getStatus, queryVo.getStatus()));
        return Result.ok(result);
    }

    @Operation(summary = "根据用户id更新账号状态")
    @PostMapping(value = "updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam BaseStatus status) {
        userInfoService.lambdaUpdate().eq(UserInfo::getId, id).set(UserInfo::getStatus, status).update();
        return Result.ok();
    }
}
