package com.snsprj.user.demo.service.impl;

import com.snsprj.user.demo.service.HelloService.Iface;
import org.apache.thrift.TException;

public class HelloServiceImpl implements Iface {

    public String sayHello(String name) throws TException {
        System.out.println("sayHello, name is " + name);
        return "Hello " + name;
    }
}
