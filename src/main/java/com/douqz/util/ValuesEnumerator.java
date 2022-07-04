package com.douqz.util;


import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * 使用
 *
 * @author yui
 */
public class ValuesEnumerator<T> implements Enumeration<T> {

    private int pos;
    private final int size;

    private final String name;

    private final List<Map.Entry<String, T>> list;

    public ValuesEnumerator(List<Map.Entry<String, T>> headers, String name) {
        this.pos = 0;
        this.name = name;
        this.list = headers;
        this.size = headers.size();
    }


    public boolean hasMoreElements() {
        if (this.list.isEmpty() || this.pos >= this.size) {
            return false;
        }
        for (int i = pos; i < size; i++) {
            String key = this.list.get(i).getKey();
            if (key.equalsIgnoreCase(this.name)) {
                return true;
            }
            this.pos++;
        }
        return false;
    }

    public T nextElement() {
        return this.list.get(this.pos++).getValue();
    }
}