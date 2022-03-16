package com.example.es.mapper;

import com.example.es.entity.TbScore;
import com.example.es.vo.AcrossDbSelect;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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

    @Select("SELECT COUNT(*) from tb_score")
    Integer count();

    @Select("select ts.*,ui.username as userName from tb_score ts left JOIN user_info ui on ts.userid = ui.id")
    List<AcrossDbSelect> acrossDbSelect();

    @Select("SELECT count(count2) FROM (select COUNT(0) as count2,subject from tb_score ts GROUP BY `subject`) data group by data.subject,count2")
    List<Integer> subQuerySelect();
}
