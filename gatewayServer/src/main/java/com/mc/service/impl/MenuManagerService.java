package com.mc.service.impl;

import com.mc.dao.MenuManagerDao;
import com.mc.entity.FormMap;
import com.mc.entity.Menu;
import com.mc.service.IMenuManagerService;
import com.mc.utils.DateUtils;
import com.mc.utils.MenuTree;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuManagerService implements IMenuManagerService {

    @Resource
    private MenuManagerDao menuManagerDao;

    @Override
    public void insertMenu(FormMap<String, Object> map) {
        map.put("createTime", DateUtils.getNowDateTime());
        menuManagerDao.insert(map);
    }

    @Override
    public void updateMenu(FormMap<String, Object> map) {
        map.put("createTime", DateUtils.getNowDateTime());
        menuManagerDao.update(map);
    }

    @Override
    public void deleteMenu(int id) {
        menuManagerDao.delete(id);
    }

    @Override
    public FormMap<String, Object> pageList(FormMap<String, Object> map) {
        int pageIndex = (int) map.get("pageIndex");
        int pageSize = (int) map.get("pageSize");
        map.put("pageIndex", (pageIndex - 1) * pageSize);
        List<FormMap<String, Object>> dataList = menuManagerDao.findMenuList(map);
        int totalCount = 0;
        if (dataList.size() > 0) {
            totalCount = menuManagerDao.findMenuListCount(map);
        }
        map.clear();
        map.put("pageIndex", pageIndex);
        map.put("pageSize", pageSize);
        map.put("resultList", dataList);
        map.put("totalCount", totalCount);
        return map;
    }

    /**
     * 获取菜单树形结构
     *
     * @return
     */
    @Override
    public List<Menu> menuTree() {
        FormMap<String, Object> formMap = new FormMap<>();
        formMap.put("parentId", 0);
        List<Menu> menuList = menuManagerDao.findAll();
        return new MenuTree(menuList).buildTree();
    }
}
