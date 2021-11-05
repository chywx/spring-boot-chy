package cn.chendahai.chy.controller;

import cn.chendahai.chy.entity.Sport;
import cn.chendahai.chy.service.SportService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chy
 * @date 2021年11月05日 上午 11:24
 */
@RequestMapping("/sport")
@RestController
public class SportController {

    private Logger logger = LoggerFactory.getLogger(SportController.class);

    @Autowired
    SportService sportService;

    @RequestMapping("/list")
    public List<Sport> list() {
        List<Sport> list = sportService.list();
        logger.info("data: {}", list);
        return list;
    }

    @RequestMapping("/getById")
    public Sport getById(String sportId) {
        Sport sport = sportService.getById(sportId);
        logger.info("sport: {}", sport);
        return sport;
    }


}
