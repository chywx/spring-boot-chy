package cn.chendahai.chy.dao;

import cn.chendahai.chy.entity.Sport;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author chy
 * @date 2021年11月05日 上午 11:27
 */
@Mapper
public interface SportMapper {

    @Select("select * from sport")
    List<Sport> list();

    @Select("select * from sport where betradar_id = #{sportId}")
    Sport getById(@Param("sportId") String sportId);
}
