package com.douqz.core;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author yui
 */
public class DefaultPrintWriter extends PrintWriter {
    public DefaultPrintWriter(Writer out) {
        super(out);
    }

    @Override
    public void flush() {
        super.flush();
    }
}
