package com.douqz.util;

import com.douqz.core.FilterWrapper;
import com.douqz.core.ServletWrapper;
import jakarta.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yui
 */
public class UrlPatterns {

    /**
     * servlet 精确匹配规则
     * urlPattern -> wrapper
     */
    private final Map<String, ServletWrapper> servletMatchAccurate = new HashMap<>();

    /**
     * servlet 路径匹配规则
     * urlPattern -> wrapper
     */
    private final Map<String, ServletWrapper> servletMatchPath = new LinkedHashMap<>();
    private boolean servletMatchPathMapSorted = false;

    /**
     * servlet 扩展名匹配规则
     * urlPattern -> wrapper
     */
    private final Map<String, ServletWrapper> servletMatchExt = new HashMap<>();


    public UrlPatterns() {
    }


    public void add(ServletWrapper sw) {
        String[] ptns = sw.getUrlPatterns();
        for (String ptn : ptns) {
            if (isPathMatch(ptn)) {
                add(ptn, sw, servletMatchPath);
            } else if (isExtensionMatch(ptn)) {
                add(ptn, sw, servletMatchExt);
            } else {
                add(ptn, sw, servletMatchAccurate);
            }
        }
    }

    public void add(FilterWrapper sw) {

    }

    @Nullable
    public ServletWrapper findMatchServlet(String uri) {
        if (!this.servletMatchPathMapSorted) {
            this.servletMatchPathMapSort();
        }
        // 精准匹配
        if (this.servletMatchAccurate.containsKey(uri)) {
            return this.servletMatchAccurate.get(uri);
        }

        for (Map.Entry<String, ServletWrapper> ent : servletMatchAccurate.entrySet()) {
            if (ent.getKey().equals(uri)) {
                return ent.getValue();
            }
        }
        // 路径匹配, 先最长路径匹配，再最短路径匹配
        for (Map.Entry<String, ServletWrapper> ent : servletMatchPath.entrySet()) {
            String ptn = ent.getKey();
            ptn = ptn.substring(0, ptn.length() - 2);
            if (uri.startsWith(ptn)) {
                return ent.getValue();
            }
        }

        // 扩展名匹配

        return null;
    }

    private static void add(String urlPattern, ServletWrapper sw, Map<String, ServletWrapper> map) {
        if (map.containsKey(urlPattern)) {
            throw new IllegalStateException("UrlPattern already exist: " + urlPattern);
        }
        map.put(urlPattern, sw);
    }

    private static boolean isPathMatch(String ptn) {
        return ptn.endsWith("/*");
    }

    private static boolean isExtensionMatch(String ptn) {
        int dotIdx = ptn.lastIndexOf(".");
        if (dotIdx == -1) {
            return false;
        }
        int sepIdx = ptn.lastIndexOf("/");
        return sepIdx <= dotIdx;
    }

    /**
     * 重排 servletMatchPathMap, 使长路径在前, 短路径在后
     */
    private void servletMatchPathMapSort() {
        // TODO
        this.servletMatchPathMapSorted = true;
    }
}
