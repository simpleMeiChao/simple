package com.mc.service.impl;

import com.mc.dao.RoleManagerDao;
import com.mc.entity.FormMap;
import com.mc.service.IRoleManagerService;
import com.mc.utils.DateUtils;
import com.mc.utils.PageUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleManagerService implements IRoleManagerService {

    @Resource
    private RoleManagerDao roleManagerDao;

    @Override
    public FormMap<String, Object> pageList(FormMap<String, Object> formMap) {
        PageUtil.pageFormMap(formMap);
        List<FormMap<String, Object>> dataList = roleManagerDao.findRoleList(formMap);
        int count = 0;
        if (dataList.size() > 0) {
            count = roleManagerDao.findRoleListCount(formMap);
        }
        formMap.put("resultList", dataList);
        formMap.put("totalCount", count);
        return formMap;
    }

    @Override
    public void addRole(FormMap<String, Object> map) {
        map.put("updateTime", DateUtils.getNowDateTime());
        map.put("updateUser", "simple1990");
        roleManagerDao.insert(map);
    }

    @Override
    public void editRole(FormMap<String, Object> map) {
        map.put("updateTime", DateUtils.getNowDateTime());
        map.put("updateUser", "simple1990");
        roleManagerDao.update(map);
    }

    @Override
    public void deleteRole(int id) {
        roleManagerDao.delete(id);
    }

    @Override
    public FormMap<String, Object> getRoles(String userName) {
        FormMap<String, Object> formMap = new FormMap<>();
        List<FormMap<String, Object>> allRoleList = roleManagerDao.findAll();
        List<FormMap<String, Object>> currentUserRoleList = roleManagerDao.findCurrentUserRole(userName);
        formMap.put("allRoleList", allRoleList);
        formMap.put("currentUserRoleList", currentUserRoleList);
        return formMap;
    }
}
