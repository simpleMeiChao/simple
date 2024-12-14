package com.mc.controller;

import com.mc.entity.CommonEntity;
import com.mc.entity.CommonResponse;
import com.mc.entity.FormMap;
import com.mc.service.IRoleManagerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/role")
public class RoleManagerController {

    @Resource
    private IRoleManagerService roleManagerService;

    @PostMapping("list")
    public CommonEntity pageList(@RequestBody FormMap<String, Object> formMap) {
        return CommonResponse.successInstance(roleManagerService.pageList(formMap));
    }

    @PostMapping("insert")
    public CommonEntity insert(@RequestBody FormMap<String, Object> formMap) {
        roleManagerService.addRole(formMap);
        return CommonResponse.successInstance();
    }

    @PostMapping("update")
    public CommonEntity update(@RequestBody FormMap<String, Object> formMap) {
        roleManagerService.editRole(formMap);
        return CommonResponse.successInstance();
    }

    @GetMapping ("/delete/{id}")
    public CommonEntity update(@PathVariable("id") String id) {
        roleManagerService.deleteRole(Integer.parseInt(id));
        return CommonResponse.successInstance();
    }

    @GetMapping ("/roles/{userName}")
    public CommonEntity roles(@PathVariable("userName") String userName) {
        return CommonResponse.successInstance(roleManagerService.getRoles(userName));
    }
}
