package com.douqz.core;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author yui
 */
public class FilterChainFactory {


    public static FilterChain createFilterChain(HttpServletRequest req, Context context) {
        String uri = req.getRequestURI();
        List<Filter> filters = context.findMatchFilters(uri);
        Servlet servlet = context.findMatchServlet(uri).getChild();

        DefaultFilterChain chain = new DefaultFilterChain(filters, servlet);

        return chain;
    }
}
