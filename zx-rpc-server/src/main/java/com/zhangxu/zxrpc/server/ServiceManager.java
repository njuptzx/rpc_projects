package com.zhangxu.zxrpc.server;

import com.zhangxu.zxrpc.Request;
import com.zhangxu.zxrpc.ServiceDescriptor;
import com.zhangxu.zxrpc.common.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理rpc暴露的服务
 */
@Slf4j
public class ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(interfaceClass);
        for(Method method : methods){
            ServiceInstance sis = new ServiceInstance(bean, method);
            ServiceDescriptor sdp = ServiceDescriptor.from(interfaceClass, method);//将ServiceDescriptor中其他数据（返回值类型，参数类型）添加进sdp

            services.put(sdp, sis);

            log.info("register service: {} {}", sdp.getClazz(), sdp.getMethod());
        }
    }
    public ServiceInstance lookup(Request request) {//根据请求查找
        ServiceDescriptor sdp = request.getService();
        return services.get(sdp);
    }

}
