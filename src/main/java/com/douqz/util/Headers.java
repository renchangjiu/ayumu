package com.douqz.util;

import io.netty.handler.codec.http.HttpHeaders;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

/**
 * servlet headers
 *
 * @author yui
 */
public class Headers {

    private final HttpHeaders nettyHeaders;

    public Headers(HttpHeaders nettyHeaders) {
        this.nettyHeaders = nettyHeaders;
    }

    public String getHeader(String name) {
        return this.nettyHeaders.get(name);
    }

    public Enumeration<String> getHeaders(String name) {
        List<String> list = this.nettyHeaders.getAll(name);
        return Collections.enumeration(list);
    }

    public List<String> getAll(String name) {
        return this.nettyHeaders.getAll(name);
    }

    public Enumeration<String> getHeaderNames() {
        Set<String> names = this.nettyHeaders.names();
        return Collections.enumeration(names);
    }

    public int getIntHeader(String name) {
        Integer res = this.nettyHeaders.getInt(name);
        if (res != null) {
            return res;
        }
        return -1;
    }

    public long getDateHeader(String name) {
        if (!this.nettyHeaders.contains(name)) {
            return -1;
        }
        Long timeMillis = this.nettyHeaders.getTimeMillis(name);
        if (timeMillis == null) {
            throw new IllegalArgumentException("The header value can't be converted to a date: " + name);
        }
        return timeMillis;
    }
}
