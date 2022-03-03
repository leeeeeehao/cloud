package com.example.es.mapper;

import com.example.es.entity.TbScore;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * User: Leehao
 * Date: 2022/3/3
 * Time: 17:05
 * Description:
 */
@Mapper
public interface TbScoreMapper {

    @Insert("insert into tb_score(userid,subject,score) values(#{userId},#{subject},#{score})")
    void addUser(TbScore tbScore);

}
