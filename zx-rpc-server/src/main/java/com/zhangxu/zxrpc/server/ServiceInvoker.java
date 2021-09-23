package com.zhangxu.zxrpc.server;

import com.zhangxu.zxrpc.Request;
import com.zhangxu.zxrpc.common.utils.ReflectionUtils;

/**
 * 调用具体服务
 */

public class ServiceInvoker {
    public Object invoke(ServiceInstance service, Request request){
        return ReflectionUtils.invoke(service.getTarget(), service.getMethod(), request.getParameters());
    }
}
