package com.douqz.util;


import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * 使用
 * @author yui
 */
public class ValuesEnumerator<T> implements Enumeration<T> {
    private int pos;

    private final List<T> list;

    public ValuesEnumerator(List<T> list) {
        this.pos = -1;
        this.list = list;
    }

    public static <T> ValuesEnumerator<T> emptyEnumerator() {
        return new ValuesEnumerator<>(Collections.emptyList());
    }

    private void findNext() {
        // for (this.next = null; this.pos < this.size; ++this.pos) {
        //     MessageBytes n1 = this.headers.getName(this.pos);
        //     if (n1.equalsIgnoreCase(this.name)) {
        //         this.next = this.headers.getValue(this.pos);
        //         break;
        //     }
        // }
        // if (pos < size) {
        //     return next
        // }
        //
        // ++this.pos;
    }

    public boolean hasMoreElements() {
        return this.list.isEmpty() || this.pos < this.list.size();
    }

    public T nextElement() {
        this.pos++;
        return this.list.get(this.pos);
    }
}