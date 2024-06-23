package cn.chendahai.chy.juejin.service;

import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@DependsOn("serviceB")
@Order(1) // 数值越小表示优先级越高
public class ServiceA implements ServiceI {
    public ServiceA() {
        System.out.println("A init ...");
    }
}
