package com.snsprj.user.demo;

import com.snsprj.user.demo.constant.UserServiceConst;
import com.snsprj.user.demo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * 测试，微服务客户端调用服务端
 */
@Slf4j
public class TestClient {

    public static void main(String[] args) {

        try  {
            // 传输层
            TTransport transport = new TSocket("localhost", UserServiceConst.USER_SERVICE_PORT, 30000);
            transport.open();

            // 协议层, 与服务端对应
            TProtocol protocol = new TBinaryProtocol(transport);

            // 创建RPC客户端
            HelloService.Client helloService = new HelloService.Client(protocol);

            //  调用服务
            String result = helloService.sayHello("xiao");

            System.out.println("result: " + result);

            // 关闭句柄s
            transport.close();
        } catch (TException e) {
            log.error("====>user client start failed!", e);
        }
    }
}
