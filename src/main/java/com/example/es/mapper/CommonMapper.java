package com.example.es.mapper;

import com.example.es.entity.CreateTableSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User: Leehao
 * Date: 2022/3/4
 * Time: 11:21
 * Description:
 */
@Mapper
public interface CommonMapper {

    /**
     * 查询数据库中的所有表名
     *
     * @param schema 数据库名
     * @return 表名列表
     */
    List<String> getAllTableNameBySchema(@Param("schema") String schema);

    /**
     * 查询建表语句
     *
     * @param tableName 表名
     * @return 建表语句
     */
    CreateTableSql selectTableCreateSql(@Param("tableName") String tableName);

    /**
     * 执行SQL
     *
     * @param sql 待执行SQL
     */
    void executeSql(@Param("sql") String sql);

}
