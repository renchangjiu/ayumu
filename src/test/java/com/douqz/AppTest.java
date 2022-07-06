package com.douqz;

import com.douqz.core.DefaultContext;
import com.douqz.servlet.TestServlet1;
import com.douqz.servlet.TestServlet2;
import com.douqz.util.Util;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
@Slf4j
public class AppTest {
    /**
     *
     */
    @Test
    public void testTomcat() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        // tomcat.setBaseDir("/tmp/tomcat");
        String contextPath = "";
        StandardContext context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());
        tomcat.getHost().addChild(context);
        Connector connector = tomcat.getConnector();
        //tomcat.addWebapp("/tomcat", "/root/apache-tomcat-8.0.53/webapps/ROOT");
        //设置不开启keep-alive
        //AbstractHttp11Protocol abstractHttp11Protocol = (AbstractHttp11Protocol)(tomcat.getConnector().getProtocolHandler());
        //abstractHttp11Protocol.setMaxKeepAliveRequests(1);
        tomcat.addServlet(contextPath, "test1", new TestServlet1());
        tomcat.addServlet(contextPath, "test2", new TestServlet2());

        context.addServletMappingDecoded("/test1", "test1");
        context.addServletMappingDecoded("/test2", "test2");

        // context.addFilterDef();
        tomcat.start();
        tomcat.getServer().await();
    }

    @Test
    public void testAyumu() throws Exception {
        HttpServer server = new HttpServer();
        DefaultContext context = new DefaultContext();
        server.setContext(context);
        server.addServlet(new TestServlet1());
        server.addServlet(new TestServlet2());
        server.start(8080);
        Thread.currentThread().join();
    }


    @Test
    public void commonTest() throws Exception {
        long start = System.currentTimeMillis();
        int total = 1024 * 1024 * 100;
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        // for (int i = 0; i < total; i++) {
        //     buf.writeByte('a');
        // }
        for (int i = 0; i < 1024 * 100; i++) {
            byte[] bytes = new byte[1024];
            for (int j = 0; j < 1024; j++) {
                bytes[j] = 'a';
            }
            buf.writeBytes(bytes);
        }

        long end = System.currentTimeMillis();
        long l = end - start;
        System.out.println();
    }

}
