package com.mc.controller;

import com.mc.entity.CommonEntity;
import com.mc.entity.CommonResponse;
import com.mc.entity.FormMap;
import com.mc.service.IMenuManagerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.ws.rs.PathParam;

/**
 * 菜单管理
 *
 * @author 86134
 * @since 2023-05-18
 */
@RestController
@RequestMapping("/menu")
public class MenuManagerController {

    @Resource
    private IMenuManagerService menuManagerService;

    @PostMapping("insert")
    public CommonEntity insertMenu(@RequestBody FormMap<String, Object> map) {
        menuManagerService.insertMenu(map);
        return CommonResponse.successInstance();
    }

    @PostMapping("update")
    public CommonEntity updateMenu(@RequestBody FormMap<String, Object> map) {
        menuManagerService.updateMenu(map);
        return CommonResponse.successInstance();
    }

    @GetMapping("delete")
    public CommonEntity deleteMenu(@PathParam("id") int id) {
        menuManagerService.deleteMenu(id);
        return CommonResponse.successInstance();
    }

    @PostMapping("list")
    public CommonEntity pageList(@RequestBody FormMap<String, Object> map) {
        return CommonResponse.successInstance(menuManagerService.pageList(map));
    }

    @GetMapping("tree")
    public CommonEntity menuTree() {
        return CommonResponse.successInstance(menuManagerService.menuTree());
    }
}
