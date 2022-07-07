package com.douqz.core;

import com.douqz.exception.NoneWebServletAnnotationException;
import jakarta.servlet.Servlet;
import jakarta.servlet.annotation.WebServlet;

/**
 * @author yui
 */
public class ServletWrapper implements Wrapper<Servlet> {

    protected volatile Servlet servlet = null;

    protected String name;

    protected String[] urlPatterns;

    protected Context context;

    public ServletWrapper(Servlet servlet, Context context) {
        this.servlet = servlet;
        this.context = context;
        WebServlet anno = servlet.getClass().getAnnotation(WebServlet.class);
        if (anno == null) {
            throw new NoneWebServletAnnotationException("This servlet no 'WebServlet' Annotation.");
        }
        this.name = anno.name();
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
