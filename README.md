# rpc_projects
# 学习目标
通过自定义一个RPC框架来学习认识反射，动态代理等内容，并为后来微服务学习打下基础。

<br>

# 关于RPC
首先了解什么叫RPC，为什么要RPC，RPC是指远程过程调用，也就是说两台服务器A，B，一个应用部署在A服务器上，想要调用B服务器上应用提供的函数/方法，由于不在一个内存空间，不能直接调用，需要通过网络来表达调用的语义和传达调用的数据。

<br>

# 具体内容

## proto模块：
### 定义server到client的协议

<br>

+ Peer : 端口
+ Response : RPC的响应
+ Request : RPC的一个请求
+ ServiceDescriptor : 服务的信息

<br>

## common模块：
### 实现反射工具



<br>

## codec模块：
### 实现序列化与反序列化

<br>

+ JSONEncoder : 基于json的序列化实现
+ JSONDecoder : 基于json的反序列化实现


<br>

## transport模块：
### 实现网络模块

<br>

+ HTTPTransportClient : 实现client方法
+ HTTPTransportServer : 实现server方法
+ TransportClient : 定义client协议接口
+ TransportServer : 定义server协议接口
+ RequestHandler : 将byte数据流抽象成handler

<br>

## server模块：
### 定义server到client的协议

<br>

+ RpcServerConfig : server配置
+ ServiceInstance : 一个具体服务
+ ServiceManager : 管理RPC暴露的服务
+ ServiceDescriptor : 服务的信息
+ ServiceInvoker : 调用具体服务
+ RpcServer : RPC服务类

<br>

## client模块：
### 定义server到client的协议

<br>

+ RandomTransportSelector : 选择需要连接的server
+ TransportSelector : 选择需要连接server接口
+ RpclientConfig : client配置
+ RpcClient : RPC客户端类
+ RemoteInvoker : 调用远程服务的代理类

<br>

## example模块：
### 使用自定义RPC开发分布式计算器

<br>

+ Client
+ server
+ CalcServiceImpl

<br>

# 启动server
>   
    14:04:31 INFO  o.e.j.u.log - Logging initialized @903ms to org.eclipse.jetty.util.log.Slf4jLog
    14:04:32 INFO  c.z.z.s.ServiceManager - register service: com.zhangxu.zxrpc.example.CalcService minus
    14:04:32 INFO  c.z.z.s.ServiceManager - register service: com.zhangxu.zxrpc.example.CalcService add
    14:04:32 INFO  o.e.j.s.Server - jetty-9.4.19.v20190610; built: 2019-06-10T16:30:51.723Z; git: afcf563148970e98786327af5e07c261fda175d3; jvm 1.8.0_291-b10
    14:04:32 INFO  o.e.j.s.h.ContextHandler - Started o.e.j.s.ServletContextHandler@131276c2{/,null,AVAILABLE}
    14:04:32 INFO  o.e.j.s.AbstractConnector - Started ServerConnector@c038203{HTTP/1.1,[http/1.1]}{0.0.0.0:3000}
    14:04:32 INFO  o.e.j.s.Server - Started @1958ms

<br>

# 然后跑一下client
>   
    14:10:16 INFO  c.z.z.c.RandomTransportSelector - connect server: Peer(host=127.0.0.1, port=3000)
    3
    2

<br>

<br>
　　

# 依赖（具体参考pom.xml文件）
>   
    <properties>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        <java.version>1.8</java.version>
        <commons.version>2.5</commons.version>
        <jetty.version>9.4.19.v20190610</jetty.version>
        <fastjson.version>1.2.44</fastjson.version>
        <lombok.version>1.18.8</lombok.version>
        <slf4j.version>1.7.26</slf4j.version>
        <logback.version>1.2.3</logback.version>
        <junit.version>4.12</junit.version>
    </properties>


<br>

# 坑
在开发过程中有几个难搞的地方，首先是Jetty的嵌入，创建了server对象，进行网络监听，然后将ServletContextHandler注册到server里面，需要ServletHolder托管Servlet处理网络请求，具体数据输入输出依靠Servlet完成。然后就是动态代理，利用JDK自带的Proxy.newProxyInstance方法创建动态代理对象，将自己实现的RemoteInvoker传进去。

<br>

# 后续开发方向
+ 重写Jetty Servlet的线程池
+ 开发注册中心
+ 嵌入到SprintBoot
+ 安全问题
  

<br>
  
  
  
