package com.snsprj.user.demo;

import com.snsprj.user.demo.constant.UserServiceConst;
import com.snsprj.user.demo.service.HelloService;
import com.snsprj.user.demo.service.HelloService.Iface;
import com.snsprj.user.demo.service.HelloService.Processor;
import com.snsprj.user.demo.service.impl.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * thrift service 微服务服务端启动入口
 */
@Slf4j
public class App {

    public static void main(String[] args) {

        System.out.println("====>user service starting...");

        initServer();
    }

    private static void initServer(){
        try {
            // 传输层（Transport），设置监听端口
            TServerSocket serverSocket = new TServerSocket(UserServiceConst.USER_SERVICE_PORT);

            // 协议层
            Factory protocolFactory = new Factory(true, true);

            // 处理层（Processor）
            HelloServiceImpl handler = new HelloServiceImpl();
            Processor<HelloServiceImpl> processor = new Processor<>(handler);

            // 服务层(Server)
            // TSimpleServer/TThreadPoolServer是阻塞服务模型
            // TNonblockingServer/THsHaServer/TThreadedSelectotServer是非阻塞服务模型(NIO)
            TServer server = new TThreadPoolServer(
                new TThreadPoolServer.Args(serverSocket).protocolFactory(protocolFactory).processor(processor));

            // 启动监听服务
            server.serve();
            log.info("====>user service started");
        } catch (Exception e) {
            log.error("====>user service start failed!", e);
        }
    }

    private static void initServer2(){
        try {
            TProcessor tprocessor = new Processor<Iface>(new HelloServiceImpl());

            // 单线程服务模型
            // 传输方式
            // 1. TSocket：阻塞型 socket，用于客户端，采用系统函数 read 和 write 进行读写数据。
            // 2. TServerSocket：非阻塞型 socket，用于服务器端，accept 到的 socket 类型都是 TSocket（即阻塞型 socket）。
            TNonblockingServerSocket serverSocket = new TNonblockingServerSocket(UserServiceConst.USER_SERVICE_PORT);

//            TServer.Args tArgs = new TServer.Args(serverSocket);
            TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverSocket);

            tArgs.processor(tprocessor);

            // 传输协议
            // TBinaryProtocol：是Thrift的默认协议，使用二进制编码格式进行数据传输，基本上直接发送原始数据
            // TCompactProtocol：压缩的、密集的数据传输协议，基于Variable-length quantity的zigzag 编码格式
            // TJSONProtocol：以JSON数据编码协议进行数据传输
            // TDebugProtocol：常常用以编码人员测试，以文本的形式展现方便阅读
            tArgs.protocolFactory(new TBinaryProtocol.Factory());

            // TSimpleServer/TThreadPoolServer 是阻塞服务模型
            // TNonblockingServer/THsHaServer/TThreadedSelectorServer 是非阻塞服务模型(NIO)
            TServer server = new TThreadedSelectorServer(tArgs);

            server.serve();

        } catch (TTransportException e) {
            log.error("====>user service start failed!", e);
        }
    }
}
