package com.douqz.core;

import jakarta.servlet.Servlet;

/**
 * @author yui
 */
public interface Wrapper {
    String getName();

    String[] getUrlPatterns();

    boolean uriMatch(String uri);

    Servlet getServlet();

    // void invokeServeltMethod
}
