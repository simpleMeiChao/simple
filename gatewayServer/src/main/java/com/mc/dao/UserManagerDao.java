package com.mc.dao;

import com.mc.entity.FormMap;

import java.util.List;

public interface UserManagerDao {

    int insert(FormMap<String, Object> map);

    int update(FormMap<String, Object> map);

    int delete(FormMap<String, Object> map);

    int insertUserRoleJoin(FormMap<String, Object> map);

    List<FormMap<String, Object>> findUserByName(String userName);

    List<String> queryRoles(int userId);

    List<FormMap<String, Object>> findUserList(FormMap<String, Object> map);

    int findUserListCount(FormMap<String, Object> map);
}
