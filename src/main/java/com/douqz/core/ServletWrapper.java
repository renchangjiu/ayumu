package com.douqz.core;

import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.WebServlet;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yui
 */
public class ServletWrapper implements Wrapper<Servlet> {

    private final Servlet servlet;

    private String name;

    private final String[] urlPatterns;

    private final Context context;

    public ServletWrapper(Servlet servlet, Context context) {
        this.servlet = servlet;
        this.context = context;
        Class<? extends Servlet> clazz = servlet.getClass();
        WebServlet anno = clazz.getAnnotation(WebServlet.class);
        if (anno == null) {
            throw new IllegalStateException("Servlet class no 'WebServlet' Annotation.");
        }

        this.name = anno.name();
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
    public Servlet getChild() {
        return servlet;
    }

    @Override
    public Context getContext() {
        return this.context;
    }
}
