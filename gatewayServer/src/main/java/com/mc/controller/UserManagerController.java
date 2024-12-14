package com.mc.controller;

import com.mc.entity.CommonEntity;
import com.mc.entity.CommonResponse;
import com.mc.entity.FormMap;
import com.mc.enums.StatusCode;
import com.mc.exception.BusinessException;
import com.mc.service.IUserManagerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserManagerController {

    @Resource
    private IUserManagerService userManagerService;

    @PostMapping("insert")
    public CommonEntity insertUserInfos(@RequestBody FormMap<String, Object> formMap) {
        if (StringUtils.isBlank((String) formMap.get("userName")) || StringUtils.isBlank((String) formMap.get("userNameCn"))) {
            throw new BusinessException(StatusCode.ERROR.statusCode(), "用户名或用户中文名不能为空");
        }
        if (StringUtils.isBlank((String) formMap.get("password"))) {
            throw new BusinessException(StatusCode.ERROR.statusCode(), "密码不能为空");
        }
        userManagerService.insertUserInfo(formMap);
        return CommonResponse.successInstance();
    }

    @PostMapping("login")
    public CommonEntity login(@RequestBody FormMap<String, Object> formMap) {
        if (StringUtils.isBlank((String) formMap.get("userName"))) {
            throw new BusinessException(StatusCode.ERROR.statusCode(), "用户名不能为空");
        }
        if (StringUtils.isBlank((String) formMap.get("password"))) {
            throw new BusinessException(StatusCode.ERROR.statusCode(), "密码不能为空");
        }
        return CommonResponse.successInstance(userManagerService.findUserByName(formMap));
    }

    @PostMapping("redis")
    public CommonEntity getRedisValue(@RequestBody FormMap<String, Object> formMap) {
        String key = (String) formMap.get("token");
        return CommonResponse.successInstance(userManagerService.getRedisValue(key));
    }

    @PostMapping("list")
    public CommonEntity pageList(@RequestBody FormMap<String, Object> formMap) {
        return CommonResponse.successInstance(userManagerService.queryPage(formMap));
    }

    @PostMapping("update")
    public CommonEntity update(@RequestBody FormMap<String, Object> formMap) {
        userManagerService.update(formMap);
        return CommonResponse.successInstance();
    }

    @PostMapping("delete")
    public CommonEntity delete(@RequestBody FormMap<String, Object> formMap) {
        userManagerService.delete(formMap);
        return CommonResponse.successInstance();
    }
}
