package com.snsprj.user.demo;

import com.snsprj.user.demo.service.HelloService.Client;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class TestClient {

    public static void main(String[] args) {

        System.out.println("====>user client starting...");

        try (TTransport transport = new TSocket("localhost", 8989, 30000)) {

            TProtocol protocol = new TBinaryProtocol(transport);

            Client client = new Client(protocol);
            transport.open();
            String result = client.sayHello("xiao");

            System.out.println("result is " + result);

        } catch (TException e) {
            System.out.println("====>user client start failed!");
        }
    }
}
