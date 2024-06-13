package cn.chendahai.chy.demo.controller;

import cn.chendahai.chy.demo.config.TestConfig;
import lombok.Data;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

    @RequestMapping("/testBody1")
    public String testBody1(@RequestBody String body) {
        logger.info("body: {}", body);
        return "success→" + body;
    }

    @RequestMapping("/testBody2")
    public String testBody2(@RequestBody TestConfig testConfig) {
        logger.info("body: {}", testConfig);
        return "success→" + testConfig;
    }

    @RequestMapping("/testResut1")
    public String testResut1() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaa", "bbb");
        return jsonObject.toString();
    }

    @RequestMapping("/testResut2")
    public JSONObject testResut2() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aaa", "bbb");
        return jsonObject;
    }

    @RequestMapping("/testResut3")
    public Map<String, String> testResut3() {
        Map<String, String> map = new HashMap<>();
        map.put("aaa", "bbb");
        return map;
    }

    @PostMapping("/testForm")
//    public String testForm(@RequestParam Map<String, Object> params) {
//    public String testForm(Map<String, Object> params) { // 获取不到
//    public String testForm(com.alibaba.fastjson.JSONObject params) {
//    public String testForm(@RequestBody BGaming params) {
    public String testForm(@RequestBody String params) {
        logger.info("params: {}", params);
        System.out.println(params);
        return "testform \t" + LocalDateTime.now();
    }

}

@Data
class BGaming {
    private String k1;
    private String k2;
}
