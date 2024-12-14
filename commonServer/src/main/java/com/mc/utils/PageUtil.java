package com.mc.utils;

import com.mc.entity.FormMap;

public class PageUtil {

    public static void pageFormMap(FormMap<String, Object> map) {
        int pageIndex = (int) map.get("pageIndex");
        int pageSize = (int) map.get("pageSize");
        map.put("pageIndex", (pageIndex - 1) * pageSize);
    }
}
