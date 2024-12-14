package com.mc.utils;

import java.util.Locale;
import java.util.UUID;

public class UUIDUtils {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().toLowerCase(Locale.ROOT).replaceAll("-", "");
    }
}
