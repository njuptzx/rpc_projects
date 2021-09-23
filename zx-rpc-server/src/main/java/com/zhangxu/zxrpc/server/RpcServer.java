package com.zhangxu.zxrpc.server;

import com.zhangxu.zxrpc.Request;
import com.zhangxu.zxrpc.Response;
import com.zhangxu.zxrpc.codec.Decoder;
import com.zhangxu.zxrpc.codec.Encoder;
import com.zhangxu.zxrpc.common.utils.ReflectionUtils;
import com.zhangxu.zxrpc.transport.RequestHandler;
import com.zhangxu.zxrpc.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
@Slf4j
public class RpcServer {
    private RpcServerConfig config;
    private TransportServer net;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RpcServer(RpcServerConfig config) {
        this.config = config;

        //创建网络模块net
        this.net = ReflectionUtils.newInstance(config.getTransportClass());
        this.net.init(config.getPort(), this.handler);
        //序列化 codec
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());

        //反序列化 service
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();
    }



    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        this.net.start();
    }

    public void stop() {
        this.net.stop();
    }


    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResp) {
            Response resp = new Response();

            try {
                byte[] inBytes = IOUtils.readFully(receive, receive.available());//读入数据
                Request request = decoder.decode(inBytes, Request.class);//反序列化为request类型
                log.info("get requset: {}", request);
                ServiceInstance sis = serviceManager.lookup(request);//根据request找到服务
                Object ret = serviceInvoker.invoke(sis, request);//开始调用
                resp.setData(ret);

            } catch (Exception e) {
                log.warn(e.getMessage(), e);
                resp.setCode(1);//返回置成失败
                resp.setMessage("RpcServer got error: " + e.getClass().getName() + " : " + e.getMessage());
            } finally {
                try {
                    byte[] outBytes = encoder.encode(resp);
                    toResp.write(outBytes);
                    log.info("response client");
                } catch (IOException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
    };
}
