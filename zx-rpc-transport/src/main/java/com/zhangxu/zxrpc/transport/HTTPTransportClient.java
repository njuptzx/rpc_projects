package com.zhangxu.zxrpc.transport;

import com.zhangxu.zxrpc.Peer;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPTransportClient implements TransportClient{
    private  String url;
    @Override
    public void connect(Peer peer) {
        this.url = "http://" + peer.getHost() + ":" + peer.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            HttpURLConnection httpConn = (HttpURLConnection) new URL(url).openConnection();
            httpConn.setDoOutput(true);//需要输出
            httpConn.setDoInput(true);//需要读数据
            httpConn.setUseCaches(false);
            httpConn.setRequestMethod("POST");

            httpConn.connect();
            IOUtils.copy(data, httpConn.getOutputStream());//发送data数据给服务端

            int resultCode = httpConn.getResponseCode();
            if(resultCode == HttpURLConnection.HTTP_OK) {//200
                return httpConn.getInputStream();
            } else {
                return httpConn.getErrorStream();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void close() {

    }
}
