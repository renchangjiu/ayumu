package com.douqz.core;

import jakarta.annotation.Nullable;
import jakarta.servlet.Filter;
import jakarta.servlet.Servlet;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yui
 */
public class DefaultContext implements Context {

    /**
     * servlets
     */
    protected final Map<String, ServletWrapper> servlets = new ConcurrentHashMap<>();

    /**
     * filters
     */
    protected final Map<String, FilterWrapper> filters = new ConcurrentHashMap<>();

    @Override
    public void addServlet(ServletWrapper wrapper) {
        synchronized (DefaultContext.class) {
            Servlet servlet = wrapper.getChild();
            // check servlet
            for (Map.Entry<String, ServletWrapper> ent : servlets.entrySet()) {
                String name = ent.getKey();
                ServletWrapper sw = ent.getValue();

                // check servlet instance unique
                if (sw.getChild().equals(servlet)) {
                    throw new IllegalStateException("Servlet already exists.");
                }

                // check servlet name unique
                if (name.equals(wrapper.getName())) {
                    throw new IllegalStateException("Servlet's name already exists.");
                }
            }
            this.servlets.put(wrapper.getName(), wrapper);
        }
    }

    @Override
    public void addFilter(FilterWrapper wrapper) {
        synchronized (DefaultContext.class) {
            Filter filter = wrapper.getChild();
            // check filter
            for (Map.Entry<String, FilterWrapper> ent : filters.entrySet()) {
                String name = ent.getKey();
                FilterWrapper sw = ent.getValue();

                // check filter instance unique
                if (sw.getChild().equals(filter)) {
                    throw new IllegalStateException("Filter already exists.");
                }

                // check filter name unique
                if (name.equals(wrapper.getName())) {
                    throw new IllegalStateException("Filter's name already exists.");
                }
            }

            this.filters.put(wrapper.getName(), wrapper);
        }
    }

    @Override
    public Collection<ServletWrapper> findServlets() {
        synchronized (servlets) {
            return this.servlets.values();
        }
    }

    @Override
    @Nullable
    public ServletWrapper findMatchServlet(String uri) {
        for (Map.Entry<String, ServletWrapper> ent : servlets.entrySet()) {
            ServletWrapper val = ent.getValue();
            if (val.uriMatch(uri)) {
                return val;
            }
        }
        return null;
    }

    @Override
    public List<Filter> findMatchFilters(String uri) {
        List<Filter> res = new ArrayList<>();
        for (Map.Entry<String, FilterWrapper> ent : filters.entrySet()) {
            FilterWrapper val = ent.getValue();
            if (val.uriMatch(uri)) {
                res.add(val.getChild());
            }
        }
        return res;
    }
}
