package com.mc.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

public class FormMap<K, V> extends HashMap<K, V> implements Serializable {

    private static final long SERIAL_VERSION_ID = 1L;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof FormMap) {
            FormMap formMap = (FormMap) o;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash();
    }
}
