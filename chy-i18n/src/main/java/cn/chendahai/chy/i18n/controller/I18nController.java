package cn.chendahai.chy.i18n.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/i18n")
public class I18nController {

    @Value("${spring.application.name}")
    private String applicationName;

    private Logger logger = LoggerFactory.getLogger(I18nController.class);

    @Autowired
    MessageSource messageSource;

    @GetMapping("/hello")
    public String hello() {
        String message = messageSource.getMessage("user.name", null, LocaleContextHolder.getLocale());
        return message;
    }

    @GetMapping("/demo")
    public String demo() {
        return messageSource.getMessage("demo", null, LocaleContextHolder.getLocale());
    }

    /**
     * http://localhost:9891/i18n/age?lang=en_US&age=10
     *
     * @param age
     * @return
     */
    @GetMapping("/age")
    public String age(@RequestParam(defaultValue = "18") Integer age) {
        return messageSource.getMessage("user.age", new Object[]{age}, LocaleContextHolder.getLocale());
    }

}
