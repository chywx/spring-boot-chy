package cn.chendahai.chy.request.controller;

import cn.chendahai.chy.request.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

@RestController
@RequestMapping("/request")
public class RequestController {

    @Value("${spring.application.name}")
    private String applicationName;

    private Logger logger = LoggerFactory.getLogger(RequestController.class);

    @RequestMapping("/getApplicationName")
    public String getApplicationName() {
        logger.info("application name: {}", applicationName);
        return "success→" + applicationName;
    }

    @RequestMapping("/test1")
    public String test1(HttpServletRequest request, @RequestBody Person person) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            System.out.println(stringEntry.getKey() + "\t" + stringEntry.getValue());
        }
        logger.info("test1 : {}", person);
        return "success→" + person;
    }

    @RequestMapping("/test2")
    public String test2(HttpServletRequest request, Person person) {
        return "success→" + person;

    }

}
