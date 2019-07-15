package com.snsprj.user.demo;

import com.snsprj.user.demo.service.HelloService.Iface;
import com.snsprj.user.demo.service.HelloService.Processor;
import com.snsprj.user.demo.service.impl.HelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class App {

    public static void main(String[] args) {

        System.out.println("====>user service starting...");

        try {
            TProcessor tprocessor = new Processor<Iface>(new HelloServiceImpl());

            // 单线程服务模型
            TServerSocket serverTransport = new TServerSocket(8989);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();

            System.out.println("====>user service start successful!");
        } catch (TTransportException e) {
            System.out.println("====>user service start failed!");
        }
    }
}
