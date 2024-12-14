package com.mc.dao;

import com.mc.entity.FormMap;

import java.util.List;

public interface RoleManagerDao {

    List<FormMap<String, Object>> findRoleList(FormMap<String, Object> map);

    int findRoleListCount(FormMap<String, Object> map);

    int insert(FormMap<String, Object> map);

    int update(FormMap<String, Object> map);

    int delete(int id);

    List<FormMap<String, Object>> findAll();

    List<FormMap<String, Object>> findCurrentUserRole(String userName);
}
