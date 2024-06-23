package cn.chendahai.chy.juejin.service;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(0)
public class ServiceB implements ServiceI {
    public ServiceB() {
        System.out.println("B init ...");
    }
}
