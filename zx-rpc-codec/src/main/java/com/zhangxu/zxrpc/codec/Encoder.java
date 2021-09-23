package com.zhangxu.zxrpc.codec;

/**
 * 序列化：将对象转成二进制数组
 */

public interface Encoder {
    byte[] encode(Object obj);
}
