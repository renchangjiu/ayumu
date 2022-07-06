package com.douqz.util;

import com.douqz.exception.NotSupportCurrentlyException;
import jakarta.servlet.http.Cookie;

import java.util.List;

/**
 * @author yui
 */
public class Cookies {

    /**
     * Cookie 解析标志。表示 Server Cookies 已转换为面向用户的 Cookie 对象。
     */
    private boolean cookiesParsed = false;

    private final List<String> cookies;

    public Cookies(List<String> cookies) {
        this.cookies = cookies;
    }

    public Cookie[] getCookies() {
        if (this.cookies.isEmpty()) {
            return null;
        }
        this.handleParseCookie();

        Cookie ck = new Cookie();
        throw new NotSupportCurrentlyException();
    }

    private void handleParseCookie() {
        if (this.cookiesParsed) {
            return;
        }
        this.parseCookie();
    }

    private void parseCookie() {


        this.cookiesParsed = true;
    }
}
