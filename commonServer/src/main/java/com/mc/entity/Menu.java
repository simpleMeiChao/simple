package com.mc.entity;

import lombok.Data;

import java.util.List;

/**
 * 菜单树实体
 *
 * @author 86134
 * @since 2023-05-18
 */
@Data
public class Menu {

    private long id; // id

    private String menuName; // 菜单名

    private String menuRoute; // 菜单路由

    private long parentId; // 父级id

    private List<Menu> children; // 子集
}
