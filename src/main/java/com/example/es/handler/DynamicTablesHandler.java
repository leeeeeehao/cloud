package com.example.es.handler;

import com.alibaba.fastjson.JSON;
import com.example.es.config.SplitTableByMonthConfig;
import com.example.es.entity.CreateTableSql;
import com.example.es.entity.DynamicMonthTableEntity;
import com.example.es.mapper.CommonMapper;
import com.example.es.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Component
@EnableScheduling
public class DynamicTablesHandler {

//    private static final Logger logger = LoggerFactory.getLogger(DynamicTablesHandler.class);
//
//    @Autowired
//    private SplitTableByMonthConfig tableConfig;
//
//    @Autowired
//    private DataSource dataSource;
//
//    private static CommonMapper commonMapper;
//
//    private static final HashSet<String> tableNameCache = new HashSet<>();
//
//    /**
//     * 手动注入
//     */
//    public static void setCommonMapper(CommonMapper commonMapper) {
//        DynamicTablesHandler.commonMapper = commonMapper;
//    }
//
//    /**
//     * 定时任务(每月最后一天中午12点生成表并刷新配置)
//     */
//
//    @Scheduled(cron = "0 0 2 26-28 * *")
//    public void CreateWcTableJobHandler() throws Exception {
//        //获取当前年月
//        Integer year = Integer.parseInt(DateUtils.getYear());
//        Integer month = Integer.parseInt(DateUtils.getMonth());
//
//        //12月开始任务时 生成次年一月份的表
//        if (month > 11) {
//            year += 1;
//            month = 1;
//        } else {
//            //生成次月表
//            month += 1;
//        }
//        // 所有以月份分片的表
//        HashMap<String, DynamicMonthTableEntity> tables = tableConfig.getTables();
//        for (String name : tables.keySet()) {
//            String newTable = name + "_" + year + "_" + month;
//            //表缓存中未包含这个表,则创建
//            if (!tableNameCache.contains(name)) {
//                createNewTable(name, newTable);
//            }
//        }
//        //创建成功之后 刷新 actual-data-nodes
//        actualTablesRefresh(year, month);
//    }
//
//    /**
//     * 初始化起始时间到当前时间的表配置
//     */
//    @PostConstruct
//    private void intData() {
//        String year = DateUtils.getYear();
//        String month = DateUtils.getMonth();
//        actualTablesRefresh(Integer.parseInt(year), Integer.parseInt(month));
//    }
//
//    /**
//     * 动态更新表配置
//     *
//     * @param year  年
//     * @param month 月
//     */
//    public void actualTablesRefresh(Integer year, Integer month) {
//        try {
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("初始化 动态表单失败" + e.getMessage());
//        }
//    }
//
//    /**
//     * 创建真实表
//     *
//     * @param logicTableName 逻辑表名
//     * @param realTableName  真实表名
//     */
//    public void createNewTable(String logicTableName, String realTableName) {
//        // 缓存中无此表 建表
//        CreateTableSql createTableSql = commonMapper.selectTableCreateSql(logicTableName);
//        logger.info(JSON.toJSONString(createTableSql));
//        String sql = createTableSql.getCreateTable();
//        sql = sql.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
//        sql = sql.replace(logicTableName, realTableName);
//        commonMapper.executeSql(sql);
//    }
//
//
//    /**
//     * 缓存重载方法
//     *
//     * @param schemaName 待加载表名所属数据库名
//     */
//    public static void tableNameCacheReload(String schemaName) {
//        // 读取数据库中所有表名
//        List<String> tableNameList = commonMapper.getAllTableNameBySchema(schemaName);
//        // 删除旧的缓存(如果存在)
//        DynamicTablesHandler.tableNameCache.clear();
//        // 写入新的缓存
//        DynamicTablesHandler.tableNameCache.addAll(tableNameList);
//    }
}
