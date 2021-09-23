package com.zhangxu.zxrpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 表示服务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {
    private String clazz;//服务的类名
    private String method;//服务的方法名
    private String returnType;//服务的返回值类型
    private String[] parameterTypes;//服务的参数类型

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)  return true;
        if(o == null || getClass() != o.getClass()) return false;

        ServiceDescriptor that = (ServiceDescriptor) o;
        return this.toString().equals(that.toString());
    }

    @Override
    public String toString() {
        return "clazz = " + clazz + ", method = " + method + ", returnType = " + returnType + ",parameterTypes = " + Arrays.toString(parameterTypes);
    }

    public static ServiceDescriptor from(Class clazz, Method method) {
        ServiceDescriptor sdp = new ServiceDescriptor();
        sdp.setClazz(clazz.getName());
        sdp.setMethod(method.getName());
        sdp.setReturnType(method.getReturnType().getName());

        Class[] parameterClasses = method.getParameterTypes();
        String[] parameterTypes = new String[parameterClasses.length];
        for(int i = 0;i < parameterClasses.length;i++){
            parameterTypes[i] = parameterClasses[i].getName();
        }
        sdp.setParameterTypes(parameterTypes);




        return sdp;
    }


}
