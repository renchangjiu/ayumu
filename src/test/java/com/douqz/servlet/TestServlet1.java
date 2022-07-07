package com.douqz.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

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
import java.util.Map;

/**
 * @author yui
 */
@Slf4j
@WebServlet(name = "test1", urlPatterns = "/test1")
@MultipartConfig
public class TestServlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doGet");
        Enumeration<String> parameterNames = req.getParameterNames();
        Map<String, String[]> parameterMap = req.getParameterMap();
        String a = req.getParameter("a");
        String[] as = req.getParameterValues("a");
        PrintWriter writer = resp.getWriter();
        Enumeration<String> h1 = req.getHeaders("h1");
        Enumeration<String> headerNames = req.getHeaderNames();
        while (h1.hasMoreElements()) {
            String next = h1.nextElement();
            System.out.println(next);
        }
        int serverPort = req.getServerPort();
        int localPort = req.getLocalPort();
        int contentLength = req.getContentLength();
        long contentLengthLong = req.getContentLengthLong();
        String contentType = req.getContentType();
        ServletInputStream inputStream = req.getInputStream();
        String s1 = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        // Cookie[] cookies = req.getCookies();

        String scheme = req.getScheme();
        String requestURI = req.getRequestURI();
        StringBuffer requestURL = req.getRequestURL();

        writer.write("测试1-中文-Get-" + new Date());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("doPost");
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        ServletOutputStream out = resp.getOutputStream();
        String msg = "Test1-中文-Post-" + new Date();
        Map<String, String[]> parameterMap = req.getParameterMap();
        String a = req.getParameter("a");
        String[] as = req.getParameterValues("a");
        // Part c = req.getPart("c");
        // String name = c.getName();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setDefaultCharset(StandardCharsets.UTF_8.name());
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding(StandardCharsets.UTF_8.name());
        Map<String, List<FileItem>> parts = upload.parseParameterMap(req);
        for (Map.Entry<String, List<FileItem>> ent : parts.entrySet()) {
            String key = ent.getKey();
            List<FileItem> val = ent.getValue();
            for (FileItem item : val) {
                boolean formField = item.isFormField();
                if (item.isFormField()) {
                    String string = item.getString();
                    System.out.println();
                } else {
                    System.out.println();
                }

            }
        }
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
