package com.mc.service;

import com.mc.entity.FormMap;
import com.mc.entity.Menu;

import java.util.List;

public interface IMenuManagerService {

    void insertMenu(FormMap<String, Object> map);

    void updateMenu(FormMap<String, Object> map);

    void deleteMenu(int id);

    FormMap<String, Object> pageList(FormMap<String, Object> map);

    List<Menu> menuTree();
}
