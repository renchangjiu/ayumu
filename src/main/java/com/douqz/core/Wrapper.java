package com.douqz.core;

import jakarta.servlet.Servlet;

/**
 * @author yui
 */
public interface Wrapper<T> {

    String getName();

    String[] getUrlPatterns();

    boolean uriMatch(String uri);

    T getChild();


    Context getContext();

}
