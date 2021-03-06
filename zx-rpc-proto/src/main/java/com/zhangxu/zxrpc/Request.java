package com.zhangxu.zxrpc;

import lombok.Data;

/**
 * 表示RPC的一个请求
 */
@Data
public class Request {
    private ServiceDescriptor service;//请求的服务
    private Object[] parameters;//参数
}
