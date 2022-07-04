# ayumu
基于 netty 的嵌入式 web 服务器, 支持 servlet 规范。

### 示例:

```java
HttpServer server = new HttpServer();
DefaultContext context = new DefaultContext();
server.setContext(context);
server.addServlet(new TestServlet1());
server.addServlet(new TestServlet2());
server.start(8080);
Thread.currentThread().join();
```
