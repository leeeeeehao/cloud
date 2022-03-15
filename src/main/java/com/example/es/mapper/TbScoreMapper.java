package com.example.es.mapper;

import com.example.es.entity.TbScore;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * User: Leehao
 * Date: 2022/3/3
 * Time: 17:05
 * Description:
 */
@Mapper
public interface TbScoreMapper {

    @Insert("insert into tb_score(userid,subject,score,create_time) values(#{userId},#{subject},#{score},#{createTime})")
    void addUser(TbScore tbScore);

    @Select("SELECT count from (select count(1) as count from tb_score) data ")
    Integer count();
}
