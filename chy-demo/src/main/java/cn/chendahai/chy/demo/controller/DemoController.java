package cn.chendahai.chy.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Value("${spring.application.name}")
    private String applicationName;

    private Logger logger = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping("/getApplicationName")
    public String getApplicationName() {
        logger.info("application name: {}", applicationName);
        return "success→" + applicationName;
    }

}
