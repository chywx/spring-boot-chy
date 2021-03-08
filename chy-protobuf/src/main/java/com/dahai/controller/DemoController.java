package com.dahai.controller;/**
 * @author chy
 * @date 2021/3/6 0006 上午 10:08
 * Description：
 */

import com.dahai.protobuf.PersonModel.Person;
import com.dahai.vo.ResultVo;
import com.google.protobuf.InvalidProtocolBufferException;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 功能描述
 *
 * @author chy
 * @date 2021/3/6 0006
 */
@RestController
@RequestMapping("/demo")
@CrossOrigin
public class DemoController {

    @GetMapping("/person/{id}")
    public byte[] person(@PathVariable("id") Integer id) {

        Person person = Person.newBuilder()
            .setId(id)
            .setName("陈海洋")
            .setAge(18)
            .setEmail("1559843332@qq.com")
            .build();

        ResultVo resultVo = new ResultVo();
        resultVo.setData(person.toByteArray());

        return person.toByteArray();

    }


    @Test
    public void test1() throws IOException {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8881/demo/person/2";
        ResponseEntity<Resource> forEntity = restTemplate.getForEntity(url, Resource.class);
        System.out.println(forEntity.getBody());
        Person person = Person.parseFrom(forEntity.getBody().getInputStream());
        System.out.println(person);

    }

    @Test
    public void test2() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:8881/demo/person/1", String.class);
        System.out.println(forEntity.getBody());
        System.out.println(forEntity.getBody().getBytes());
    }

}
