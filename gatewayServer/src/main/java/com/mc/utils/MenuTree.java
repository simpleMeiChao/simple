package com.mc.utils;

import com.mc.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuTree {

    private List<Menu> menuList = new ArrayList<>();

    public MenuTree(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<Menu> getRootNode() {
        List<Menu> rootNode = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentId() == 0) {
                rootNode.add(menu);
            }
        }
        return rootNode;
    }

    // 构建子树
    public Menu buildChildren(Menu rootNode){
        List<Menu> childrenTree = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentId() == rootNode.getId()){
                childrenTree.add(buildChildren(menu));
            }
        }
        rootNode.setChildren(childrenTree);
        return rootNode;
    }

    // 构建树
    public List<Menu> buildTree(){
        List<Menu> menus = getRootNode();
        for (Menu menu : menus) {
            buildChildren(menu);
        }
        return menus;
    }
}
