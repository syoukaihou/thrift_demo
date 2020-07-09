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
            // TSocket：阻塞型 socket，用于客户端，采用系统函数 read 和 write 进行读写数据。
            // TServerSocket：非阻塞型 socket，用于服务器端，accept 到的 socket 类型都是 TSocket（即阻塞型 socket）。
            // ...
            TTransport transport = new TSocket("localhost", UserServiceConst.USER_SERVICE_PORT, 30000);
            transport.open();

            // 协议层, 与服务端对应
            // TBinaryProtocol：是Thrift的默认协议，使用二进制编码格式进行数据传输，基本上直接发送原始数据
            // TCompactProtocol：压缩的、密集的数据传输协议，基于Variable-length quantity的zigzag 编码格式
            // TJSONProtocol：以JSON (JavaScript Object Notation)数据编码协议进行数据传输
            // TDebugProtocol：常常用以编码人员测试，以文本的形式展现方便阅读
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
