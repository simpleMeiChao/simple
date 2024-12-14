package com.mc.service;

import com.mc.entity.FormMap;

import java.util.List;

public interface IUserManagerService {

    void insertUserInfo(FormMap<String, Object> formMap);

    FormMap<String, Object> findUserByName(FormMap<String, Object> formMap);

    Object getRedisValue(String key);

    void update(FormMap<String, Object> formMap);

    void delete(FormMap<String, Object> formMap);

    FormMap<String, Object> queryPage(FormMap<String, Object> formMap);
}
