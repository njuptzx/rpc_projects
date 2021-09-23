package com.zhangxu.zxrpc.server;

import com.zhangxu.zxrpc.codec.Decoder;
import com.zhangxu.zxrpc.codec.Encoder;
import com.zhangxu.zxrpc.codec.JSONDecoder;
import com.zhangxu.zxrpc.codec.JSONEncoder;
import com.zhangxu.zxrpc.transport.HTTPTransportServer;
import com.zhangxu.zxrpc.transport.TransportServer;
import lombok.Data;

/**
 * server配置
 */
@Data
public class RpcServerConfig {
    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;
    private Class<? extends Encoder> encoderClass = JSONEncoder.class;
    private Class<? extends Decoder> decoderClass = JSONDecoder.class;
    private int port = 3000;

}
