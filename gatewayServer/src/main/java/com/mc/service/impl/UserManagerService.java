package com.mc.service.impl;

import com.mc.dao.UserManagerDao;
import com.mc.entity.FormMap;
import com.mc.enums.ConstantEnums;
import com.mc.enums.StatusCode;
import com.mc.exception.BusinessException;
import com.mc.service.IUserManagerService;
import com.mc.utils.AESUtils;
import com.mc.utils.DateUtils;
import com.mc.utils.UUIDUtils;
import com.mc.utils.RedisUtils;
import com.mc.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户管理业务
 *
 * @author simpleMei
 * @since 2023-05-14 18:05:34
 */
@Service
@Slf4j
public class UserManagerService implements IUserManagerService {

    @Resource
    private UserManagerDao userManagerDao;

    @Resource
    RedisUtils redisUtils;

    /**
     * 新增用户
     *
     * @param formMap
     */
    @Override
    public void insertUserInfo(FormMap<String, Object> formMap) {
        String userKey = AESUtils.getRandomNumber();
        formMap.put("userKey", userKey);
        formMap.put("password", AESUtils.encrypt((String) formMap.get("password"), userKey));
        formMap.put("uid", UUIDUtils.getUUID());
        formMap.put("operator", "simpleMei");
        formMap.put("createTime", DateUtils.getNowDateTime());
        userManagerDao.insert(formMap);
        userManagerDao.insertUserRoleJoin(formMap);
    }

    /**
     * 登录 login
     *
     * @param formMap
     * @return
     */
    @Override
    public FormMap<String, Object> findUserByName(FormMap<String, Object> formMap) {
        List<FormMap<String, Object>> userList = userManagerDao.findUserByName((String) formMap.get("userName"));
        if (userList == null || userList.size() < 1) {
            throw new BusinessException(StatusCode.ERROR.statusCode(), "账号不存在");
        }
        FormMap<String, Object> userInfoMap = userList.get(0);
        String paramPassword = (String) formMap.get("password");
        String password = AESUtils.decrypt((String) userInfoMap.get("password"), (String) userInfoMap.get("userKey"));
        if (!paramPassword.equals(password)) {
            throw new BusinessException(StatusCode.ERROR.statusCode(), "密码错误");
        }
        int id = Integer.valueOf(userInfoMap.get("id") + "");
        List<String> roleName = userManagerDao.queryRoles(id);
        String rolesCommaSeparated = String.join(",", roleName);
        userInfoMap.put("userRole", rolesCommaSeparated);
        String token = JWTUtils.buildToken((String) userInfoMap.get("userName"));
        userInfoMap.put("token", token);
        redisUtils.setCacheObject(token, userInfoMap, Long.parseLong(ConstantEnums.TOKEN_VALIDITY_TIME.getConstant() + ""), TimeUnit.MILLISECONDS);
        return userInfoMap;
    }

    /**
     * 根据key查询redis
     *
     * @param key key
     * @return
     */
    @Override
    public Object getRedisValue(String key) {
        return redisUtils.getCacheObject(key);
    }

    /**
     * 修改用户
     *
     * @param formMap
     */
    @Override
    public void update(FormMap<String, Object> formMap) {
        formMap.put("updateTime", DateUtils.getNowDateTime());
        userManagerDao.update(formMap);
    }

    /**
     * 删除用户
     *
     * @param formMap
     */
    @Override
    public void delete(FormMap<String, Object> formMap) {
        userManagerDao.delete(formMap);
    }

    /**
     * 分页查询
     *
     * @param formMap
     * @return
     */
    @Override
    public FormMap<String, Object> queryPage(FormMap<String, Object> formMap) {
        int pageIndex = (int) formMap.get("pageIndex");
        int pageSize = (int) formMap.get("pageSize");
        formMap.put("pageIndex", (pageIndex - 1) * pageSize);
        List<FormMap<String, Object>> dataList = userManagerDao.findUserList(formMap);
        int totalCount = 0;
        if (dataList.size() > 0) {
            totalCount = userManagerDao.findUserListCount(formMap);
        }
        formMap.clear();
        formMap.put("pageIndex", pageIndex);
        formMap.put("pageSize", pageSize);
        formMap.put("resultList", dataList);
        formMap.put("totalCount", totalCount);
        return formMap;
    }
}
