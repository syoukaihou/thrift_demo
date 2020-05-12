package com.snsprj.user.demo.service.impl;

import com.snsprj.user.demo.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;

@Slf4j
public class HelloServiceImpl implements HelloService.Iface {

    public String sayHello(String name) throws TException {
        log.info("sayHello, name: {} ", name);
        return "Hello " + name;
    }
}
