package com.douqz.core;

import jakarta.annotation.Nullable;
import jakarta.servlet.Filter;

import java.util.*;

/**
 * @author yui
 */
public class DefaultContext implements Context {

    /**
     * servlets
     */
    protected final HashMap<String, ServletWrapper> children = new HashMap<>();

    /**
     * filters
     */
    protected final HashMap<String, FilterWrapper> filters = new HashMap<>();

    @Override
    public void addServlet(ServletWrapper child) {
        synchronized (DefaultContext.class) {
            this.children.put(child.getName(), child);
        }
    }

    @Override
    public void addFilter(FilterWrapper wrapper) {
        synchronized (DefaultContext.class) {
            this.filters.put(wrapper.getName(), wrapper);
        }
    }

    @Override
    public Collection<ServletWrapper> findServlets() {
        synchronized (children) {
            return this.children.values();
        }
    }

    @Override
    @Nullable
    public ServletWrapper findMatchServlet(String uri) {
        for (Map.Entry<String, ServletWrapper> ent : children.entrySet()) {
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
