package com.zhangxu.zxrpc.client;

import com.zhangxu.zxrpc.Peer;
import com.zhangxu.zxrpc.common.utils.ReflectionUtils;
import com.zhangxu.zxrpc.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;
@Slf4j
public class RandomTransportSelector implements TransportSelector{
    /**
     * 已经连接好的client
     * @param peers 可以连接的server端点信息
     * @param count client与server建立多少个连接
     * @param clazz client实现class
     */

    private List<TransportClient> clients;

    public RandomTransportSelector() {
       clients = new ArrayList<>();
    }

    @Override
    public synchronized void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);

        for(Peer peer : peers) {
            for(int i = 0;i < count; i++){
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect server: {}", peer);
        }
    }

    @Override
    public synchronized TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public synchronized void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public synchronized void close() {
        for(TransportClient client : clients){
            client.close();
        }
        clients.clear();
    }
}
