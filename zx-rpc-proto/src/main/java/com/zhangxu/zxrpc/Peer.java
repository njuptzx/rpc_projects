package com.zhangxu.zxrpc;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 表示网络传输的一个端口
 */
@Data
@AllArgsConstructor
public class Peer {
    private String host;
    private int port;
}

