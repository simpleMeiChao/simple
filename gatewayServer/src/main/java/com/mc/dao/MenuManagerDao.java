package com.mc.dao;

import com.mc.entity.FormMap;
import com.mc.entity.Menu;

import java.util.List;

public interface MenuManagerDao {

    int insert(FormMap<String, Object> map);

    int update(FormMap<String, Object> map);

    int delete(int id);

    List<FormMap<String, Object>> findMenuList(FormMap<String, Object> map);

    int findMenuListCount(FormMap<String, Object> map);

    List<Menu> findAll();
}
