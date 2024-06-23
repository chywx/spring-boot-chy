package cn.chendahai.chy.juejin.controller;

import cn.chendahai.chy.juejin.service.ServiceI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/juejin")
public class JueJinController {

    @Value("${spring.application.name}")
    private String applicationName;

    private Logger logger = LoggerFactory.getLogger(JueJinController.class);

    @RequestMapping("/getApplicationName")
    public String getApplicationName() {
        logger.info("application name: {}", applicationName);
        return "success→" + applicationName;
    }

    @Autowired
    List<ServiceI> serviceIList;

    /**
     * @Order：改变Bean注入的顺序
     * @DependsOn：改变Bean创建的顺序
     */
    @RequestMapping("/getServiceIList")
    public String getServiceIList() {
        logger.info("serviceIList name: {}", serviceIList);
        return "success→" + serviceIList;
    }


}
