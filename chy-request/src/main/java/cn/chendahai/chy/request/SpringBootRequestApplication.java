package cn.chendahai.chy.request;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("cn.chendahai.chy.request.filter")
public class SpringBootRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRequestApplication.class, args);
    }

}
