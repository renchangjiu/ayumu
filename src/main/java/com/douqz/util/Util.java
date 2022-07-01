package com.douqz.util;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @author yui
 */
public class Util {


    public static void printByteBuf(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index: ").append(buffer.readerIndex()).append(",")
                .append(" write index: ").append(buffer.writerIndex()).append(",")
                .append(" capacity: ").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf);
    }

    public static InetSocketAddress newInetAddress() {
        return new InetSocketAddress("127.0.0.1", 8080);
    }
}
