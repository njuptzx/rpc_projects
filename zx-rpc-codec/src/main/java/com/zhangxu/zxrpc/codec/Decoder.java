package com.zhangxu.zxrpc.codec;

/**
 * 反序列化：将二进制数组转成对象
 */

public interface Decoder {
    <T> T decode(byte[] bytes, Class<T> clazz);
}
