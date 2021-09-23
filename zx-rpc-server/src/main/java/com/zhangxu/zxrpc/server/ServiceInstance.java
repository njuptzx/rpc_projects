package com.zhangxu.zxrpc.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 表示一个具体服务
 */
@Data
@AllArgsConstructor
public class ServiceInstance {
    private Object target;//提供服务的对象
    private Method method;//相关方法
}
