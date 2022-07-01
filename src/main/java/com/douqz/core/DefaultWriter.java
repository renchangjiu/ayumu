package com.douqz.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.internal.StringUtil;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * @author yui
 */
public class DefaultWriter extends Writer {

    private final ByteBuf buf;

    public DefaultWriter(ByteBuf buf) {
        this.buf = buf;
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        this.buf.writeBytes(new String(cbuf, off, len).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }

    public static byte[] char2bytes(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }
}
