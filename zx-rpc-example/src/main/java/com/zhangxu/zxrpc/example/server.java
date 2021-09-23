package com.zhangxu.zxrpc.example;

import com.zhangxu.zxrpc.server.RpcServer;
import com.zhangxu.zxrpc.server.RpcServerConfig;

public class server {
    public static void main(String[] args) {

        RpcServer server = new RpcServer(new RpcServerConfig());
        server.register(CalcService.class, new CalcServiceImpl());
        server.start();
    }
}
