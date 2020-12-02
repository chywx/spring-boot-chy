package cn.chendahai.chy.controller;/**
 * @author lql
 * @date 2020/9/18 0018 上午 10:21
 * Description：
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 功能描述
 *
 * @author chy
 * @date 2020/9/18 0018
 */
@Controller
public class IndexController {

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", "dahai");
        return "index";
    }
}
