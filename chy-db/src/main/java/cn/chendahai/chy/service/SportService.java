package cn.chendahai.chy.service;

import cn.chendahai.chy.dao.SportMapper;
import cn.chendahai.chy.entity.Sport;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chy
 * @date 2021年11月05日 上午 11:23
 */
@Service
public class SportService {

    @Autowired
    SportMapper sportMapper;

    public List<Sport> list() {
        return sportMapper.list();
    }

    public Sport getById(String sportId) {
        return sportMapper.getById(sportId);
    }
}
