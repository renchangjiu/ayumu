package com.douqz.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @author yui
 */
public class RequestUtil {


    public static StringBuffer getRequestURL(HttpServletRequest req) {
        StringBuffer url = new StringBuffer();
        String scheme = req.getScheme();
        int port = req.getServerPort();

        url.append(scheme);
        url.append("://");
        url.append(req.getServerName());
        if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
            url.append(':');
            url.append(port);
        }
        url.append(req.getRequestURI());

        return url;
    }


    public static String getCharsetFromContentType(String contentType) {
        if (contentType == null) {
            return null;
        }
        int start = contentType.indexOf("charset=");
        if (start < 0) {
            return null;
        }
        String encoding = contentType.substring(start + 8);
        int end = encoding.indexOf(';');
        if (end >= 0) {
            encoding = encoding.substring(0, end);
        }
        encoding = encoding.trim();
        if ((encoding.length() > 2) && (encoding.startsWith("\"")) && (encoding.endsWith("\""))) {
            encoding = encoding.substring(1, encoding.length() - 1);
        }

        return encoding.trim();
    }
}
