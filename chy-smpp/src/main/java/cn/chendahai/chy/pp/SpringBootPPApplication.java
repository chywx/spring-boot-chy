package cn.chendahai.chy.pp;

import cn.chendahai.chy.pp.config.TigoSmppClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class SpringBootPPApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootPPApplication.class, args);
        log.info("start finish");
//        TigoSmppClient.start(args);
    }

}
