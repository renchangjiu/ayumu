package com.douqz.core;

import com.douqz.exception.NotSupportCurrentlyException;
import io.netty.buffer.ByteBuf;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.IOException;

/**
 * @author yui
 */
public class DefaultServletOutputStream extends ServletOutputStream {

    private final ByteBuf buf;

    // private final int

    public DefaultServletOutputStream(ByteBuf buf) {
        this.buf = buf;
    }

    @Override
    public boolean isReady() {
        throw new NotSupportCurrentlyException();
    }

    @Override
    public void setWriteListener(WriteListener listener) {
        throw new NotSupportCurrentlyException();
    }

    @Override
    public void write(int b) throws IOException {
        this.buf.writeByte(b);
    }


}
