package cn.chendahai.chy;

import cn.chendahai.chy.i18n.config.MyLocaleResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;

@SpringBootApplication
public class SpringBootI18nApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootI18nApplication.class, args);
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }

}
