package com.douqz.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 * @author yui
 */
@Slf4j
@WebServlet(name = "test1", urlPatterns = "/test1")
public class TestServlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet");
        String a = req.getParameter("a");
        PrintWriter writer = resp.getWriter();
        Enumeration<String> h1 = req.getHeaders("h1");
        while (h1.hasMoreElements()) {
            String next = h1.nextElement();
            System.out.println(next);
        }
        int serverPort = req.getServerPort();
        int localPort = req.getLocalPort();
        writer.write("测试1-中文-Get-" + new Date());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost");
        ServletOutputStream out = resp.getOutputStream();
        String msg = "Test1-中文-Post-" + new Date();
        out.write(msg.getBytes(StandardCharsets.UTF_8));
    }


    @Override
    protected long getLastModified(HttpServletRequest req) {
        return super.getLastModified(req);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doHead");
        super.doHead(req, resp);
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPut");
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doDelete");
        super.doDelete(req, resp);
    }


}
