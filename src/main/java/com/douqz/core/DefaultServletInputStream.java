package com.douqz.core;

import io.netty.buffer.ByteBuf;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.IOException;

/**
 * @author yui
 */
public class DefaultServletInputStream extends ServletInputStream {

    private final ByteBuf buf;

    private final long contentLength;

    public DefaultServletInputStream(ByteBuf buf, long contentLength) {
        this.buf = buf;
        this.contentLength = contentLength;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {

    }

    @Override
    public int read() throws IOException {
        if (this.buf.readerIndex() >= this.contentLength) {
            return -1;
        }
        return this.buf.readByte();
    }
}
