package com.douqz.core;

import jakarta.annotation.Nullable;
import jakarta.servlet.Filter;

import java.util.Collection;
import java.util.List;

/**
 * @author yui
 */
public interface Context {

    void addServlet(ServletWrapper child);

    void addFilter(FilterWrapper wrapper);

    Collection<ServletWrapper> findServlets();

    @Nullable
    ServletWrapper findMatchServlet(String uri);

    List<Filter> findMatchFilters(String uri);
}
