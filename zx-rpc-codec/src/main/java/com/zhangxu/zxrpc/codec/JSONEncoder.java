package com.zhangxu.zxrpc.codec;

import com.alibaba.fastjson.JSON;

/**
 * 基于json的序列化实现
 */

public class JSONEncoder implements Encoder{
    @Override
    public byte[] encode(Object obj) {
        return JSON.toJSONBytes(obj);//将对象转成JSON字符串的序列化数组
    }
}
