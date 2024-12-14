package com.mc.service;

import com.mc.entity.FormMap;

public interface IRoleManagerService {

    FormMap<String, Object> pageList(FormMap<String, Object> map);

    void addRole(FormMap<String, Object> map);

    void editRole(FormMap<String, Object> map);

    void deleteRole(int id);

    FormMap<String, Object> getRoles(String userName);
}
