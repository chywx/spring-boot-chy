package cn.chendahai.chy.oss.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test1")
    public String test1(@RequestParam(defaultValue = "hello") String msg) {
        logger.info("tset1 msg: {}", msg);
        return "success→" + msg;
    }

}
