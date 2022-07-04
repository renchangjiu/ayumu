package com.douqz.util;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * servlet parameters
 *
 * @author yui
 */
public class Parameters {

    private final ByteBuf content;

    private Charset charset = StandardCharsets.UTF_8;

    private final String contentType;

    private final Map<String, ArrayList<String>> paramValues = new LinkedHashMap<>();

    private boolean didQueryParameters = false;

    public Parameters(ByteBuf content, String contentType) {
        this.content = content;
        this.contentType = contentType;
    }


    public String getParameter(String name) {
        this.handleQueryParameters();
        if (this.paramValues.containsKey(name)) {
            return this.paramValues.get(name).get(0);
        }
        return null;
    }

    public String[] getParameterValues(String name) {
        this.handleQueryParameters();

        ArrayList<String> values = this.paramValues.get(name);
        if (values == null) {
            return null;
        }
        return values.toArray(new String[0]);
    }

    public Map<String, String[]> getParameterMap() {
        this.handleQueryParameters();
        Map<String, String[]> res = new HashMap<>();
        for (Map.Entry<String, ArrayList<String>> ent : paramValues.entrySet()) {
            res.put(ent.getKey(), ent.getValue().toArray(new String[0]));
        }
        return res;
    }

    public Enumeration<String> getParameterNames() {
        this.handleQueryParameters();
        return Collections.enumeration(this.paramValues.keySet());
    }

    private void handleQueryParameters() {
        if (this.didQueryParameters) {
            return;
        }
        processParameters(contentType);
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    private void processParameters(String contentType) {
        if (contentType.equalsIgnoreCase("application/x-www-form-urlencoded")) {
            this.doIfUrlencoded();
        } else {
            this.doIfMultipart();
        }
        didQueryParameters = true;
    }

    private void doIfUrlencoded() {
        String con = this.content.toString(this.charset);
        String[] split = con.split("&");
        for (String s : split) {
            String[] pam = s.split("=");
            if (pam.length != 2) {
                continue;
            }
            String key = pam[0];
            String val = this.urlDecode(pam[1]);
            if (this.paramValues.containsKey(key)) {
                this.paramValues.get(key).add(val);
            } else {
                ArrayList<String> vals = new ArrayList<>();
                vals.add(val);
                this.paramValues.put(key, vals);
            }
        }
    }

    private void doIfMultipart() {
        String con = this.content.toString(this.charset);
        // DiskFileItemFactory factory = new DiskFileItemFactory();
        // ServletFileUpload upload = new ServletFileUpload(factory);
        // upload.setHeaderEncoding(this.charset.name());
        // List<FileItem> fileItems = upload.parseRequest(new DefaultRequest(null));
        System.out.println();
    }

    private String urlDecode(String s) {
        try {
            return URLDecoder.decode(s, this.charset.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


}
