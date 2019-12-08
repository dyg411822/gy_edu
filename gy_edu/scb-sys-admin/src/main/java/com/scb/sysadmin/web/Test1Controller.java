package com.scb.sysadmin.web;

import com.scb.common.util.RedisUtils;
import com.scb.common.vo.Result;
import com.scb.sysadmin.annotation.PermissionSign;
import com.scb.sysadmin.constant.PermissionLogicEnum;
import com.scb.sysadmin.constant.Permissions;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("测试")
public class Test1Controller {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("/api/test1")
    public Result m1() {
        return Result.succeed("T1");
    }

    @GetMapping("/api/test2")
    @PermissionSign(value = {Permissions.COMMON_USE, "RR", "R1"}, logic = PermissionLogicEnum.OR)
    public Result m2() {
        return Result.succeed("T2");
    }

    @GetMapping("/api/test3")
    @PermissionSign({"R1", "RX"})
    public Result m3() {
        redisUtils.set("AIAI", "OKGG");
        throw new RuntimeException("----- OMG -----");
//        return Result.succeed("T3");
    }

    @ApiOperation(value = "计算+", notes = "加法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "a", paramType = "path", value = "数字a", required = true, dataType = "Long"),
            @ApiImplicitParam(name = "b", paramType = "path", value = "数字b", required = true, dataType = "Long")
    })
    @GetMapping("/{a}/{b}")
    public Integer get(@PathVariable Integer a, @PathVariable Integer b) {
        return a + b;
    }
}
