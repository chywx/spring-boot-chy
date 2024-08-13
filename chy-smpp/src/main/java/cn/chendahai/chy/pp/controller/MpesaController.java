package cn.chendahai.chy.pp.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mpesa")
public class MpesaController {

    @Value("${spring.application.name}")
    private String applicationName;

    private Logger logger = LoggerFactory.getLogger(MpesaController.class);

    @RequestMapping("/**")
    public String all(@RequestBody JSONObject body, HttpServletRequest request) {
        logger.info("uri: {}", request.getRequestURI());
        logger.info("body: {}", body.toString());
        logger.info("all params: {}", JSONObject.toJSONString(request.getParameterMap()));
        return "success→" + applicationName;
    }

}
