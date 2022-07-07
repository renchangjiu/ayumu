package com.douqz.core;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yui
 */
public class DefaultFilterChain implements FilterChain {


    /**
     * 链上的 filter
     */
    private List<Filter> filters;

    /**
     * 最终的 servlet
     */
    private Servlet servlet;

    /**
     * 当前的 filter 位置
     */
    private int pos = 0;


    public DefaultFilterChain(List<Filter> filters, Servlet servlet) {
        this.filters = filters;
        this.servlet = servlet;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (this.filters.isEmpty() && this.servlet == null) {
            resp.setStatus(HttpResponseStatus.NOT_FOUND.code());
        }

        if (this.pos < this.filters.size()) {
            this.filters.get(pos++).doFilter(req, resp, this);
        } else if (this.servlet != null) {
            this.servlet.service(req, resp);
        }

    }


}
