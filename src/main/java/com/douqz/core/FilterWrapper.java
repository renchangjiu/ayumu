package com.douqz.core;

import com.douqz.exception.NoneWebServletAnnotationException;
import jakarta.servlet.Filter;
import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.WebFilter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yui
 */
public class FilterWrapper implements Wrapper<Filter> {

    protected Context context;

    private final Filter filter;

    protected String name;

    protected String[] urlPatterns;

    public FilterWrapper(Filter filter, Context context) {
        this.filter = filter;
        this.context = context;
        Class<? extends Filter> clazz = filter.getClass();
        WebFilter anno = clazz.getAnnotation(WebFilter.class);
        if (anno == null) {
            throw new NoneWebServletAnnotationException("This servlet no 'WebFilter' Annotation.");
        }

        this.name = anno.filterName();
        if (StringUtils.isEmpty(this.name)) {
            this.name = clazz.getSimpleName();
        }

        this.urlPatterns = anno.urlPatterns();
    }

    public String getName() {
        return name;
    }

    public String[] getUrlPatterns() {
        return urlPatterns;
    }

    /**
     * TODO: 复杂的 uri 匹配规则
     */
    @Override
    public boolean uriMatch(String uri) {
        for (String ptn : urlPatterns) {
            if (ptn.equals(uri)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Filter getChild() {
        return this.filter;
    }

    @Override
    public Context getContext() {
        return this.context;
    }
}
