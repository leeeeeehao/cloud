package com.example.es.handler;

import com.alibaba.fastjson.JSON;
import com.example.es.config.SplitTableByMonthConfig;
import com.example.es.entity.CreateTableSql;
import com.example.es.entity.DynamicMonthTableEntity;
import com.example.es.mapper.CommonMapper;
import com.example.es.util.DateUtils;
import org.apache.shardingsphere.core.exception.ShardingConfigurationException;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

@Component
@EnableScheduling
public class DynamicTablesHandler {

    private static final Logger logger = LoggerFactory.getLogger(DynamicTablesHandler.class);

    @Autowired
    private SplitTableByMonthConfig tableConfig;

    @Autowired
    private DataSource dataSource;

    private static CommonMapper commonMapper;

    private static final HashSet<String> tableNameCache = new HashSet<>();

    /**
     * 手动注入
     */
    public static void setCommonMapper(CommonMapper commonMapper) {
        DynamicTablesHandler.commonMapper = commonMapper;
    }

    /**
     * 定时任务(每月最后一天中午12点生成表并刷新配置)
     */

    @Scheduled(cron = "0 0 2 26-28 * *")
    public void CreateWcTableJobHandler() throws Exception {
        //获取当前年月
        Integer year = Integer.parseInt(DateUtils.getYear());
        Integer month = Integer.parseInt(DateUtils.getMonth());

        //12月开始任务时 生成次年一月份的表
        if (month > 11) {
            year += 1;
            month = 1;
        } else {
            //生成次月表
            month += 1;
        }
        // 所有以月份分片的表
        HashMap<String, DynamicMonthTableEntity> tables = tableConfig.getTables();
        for (String name : tables.keySet()) {
            String newTable = name + "_" + year + "_" + month;
            //表缓存中未包含这个表,则创建
            if (!tableNameCache.contains(name)) {
                createNewTable(name, newTable);
            }
        }
        //创建成功之后 刷新 actual-data-nodes
        actualTablesRefresh(year, month);
    }

    /**
     * 初始化起始时间到当前时间的表配置
     */
    @PostConstruct
    private void intData() {
        String year = DateUtils.getYear();
        String month = DateUtils.getMonth();
        actualTablesRefresh(Integer.parseInt(year), Integer.parseInt(month));
    }

    /**
     * 动态更新表配置
     *
     * @param year  年
     * @param month 月
     */
    public void actualTablesRefresh(Integer year, Integer month) {
        try {
            //获取shadingJdbc配置
            ShardingDataSource dataSource = (ShardingDataSource) this.dataSource;
            HashMap<String, DynamicMonthTableEntity> tables = tableConfig.getTables();
            final Set<String> names = tables.keySet();
            //判断是否需要动态刷新配置
            if (names == null || names.size() == 0) {
                logger.error("没有动态分表配置");
                return;
            }
            //为每张动态表刷新配置
            for (String name : names) {
                TableRule tableRule = null;
                try {
                    //获取配置规则
                    tableRule = dataSource.getShardingContext().getShardingRule().getTableRule(name);
                } catch (ShardingConfigurationException e) {
                    logger.error("报错啦" + e.getMessage());

                }
                //配置sharding表对应的实际表节点
                List<DataNode> dataNodes = tableRule.getActualDataNodes();
                Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);
                actualDataNodesField.setAccessible(true);
                //新的表节点集合
                List<DataNode> newDataNodes = new ArrayList<>();
                //起始时间点
                DynamicMonthTableEntity dynamicMonthTableEntity = tables.get(name);
                int yearTime = dynamicMonthTableEntity.getStartYear();
                int monthTime = dynamicMonthTableEntity.getStartMonth();
                //获取sharding表名
                String dataSourceName = dataNodes.get(0).getDataSourceName();
                //数据节点真实表后缀名，用于校验表是否存在
                List<String> dataNodeSuffix = new ArrayList<>();
                //往表节点中更新数据
                while (true) {
                    int tempTime = yearTime;
                    //起始年小于当前年
                    if (yearTime < year) {
                        while (yearTime <= year) {
                            //起始年份的年份配置节点生成
                            if (tempTime == yearTime) {
                                for (int j = monthTime; j < 13; j++) {
                                    dataNodeSuffix.add(name + "_" + yearTime + "_" + j);
                                    DataNode dataNode = new DataNode(dataSourceName + "." + name + "_" + yearTime + "_" + j);
                                    newDataNodes.add(dataNode);
                                }
                                yearTime++;
                                //中间年份的年份配置节点生成
                            } else if (yearTime > tempTime && yearTime != year) {
                                for (int j = 1; j < 13; j++) {
                                    dataNodeSuffix.add(name + "_" + yearTime + "_" + j);
                                    DataNode dataNode = new DataNode(dataSourceName + "." + name + "_" + year + "_" + j);
                                    newDataNodes.add(dataNode);
                                }
                                yearTime++;
                            } else {
                                break;
                            }
                        }
                        //当前年份的年份配置节点生成
                        if (yearTime == year) {
                            for (int j = 1; j < month + 1; j++) {
                                dataNodeSuffix.add(name + "_" + yearTime + "_" + j);
                                DataNode dataNode = new DataNode(dataSourceName + "." + name + "_" + yearTime + "_" + j);
                                newDataNodes.add(dataNode);
                            }
                            yearTime++;
                            break;
                        }
                    }

                    //起始年等于当前年
                    if (yearTime == year) {
                        for (int j = monthTime; j < month + 1; j++) {
                            dataNodeSuffix.add(name + "_" + yearTime + "_" + j);
                            DataNode dataNode = new DataNode(dataSourceName + "." + name + "_" + yearTime + "_" + j);
                            newDataNodes.add(dataNode);
                        }
                        yearTime++;
                    }

                    if (yearTime > year) {
                        break;
                    }
                }
                //如果动态表节点不为空,查看表是否存在
                if (!CollectionUtils.isEmpty(dataNodeSuffix)) {
                    List<String> notExistTables = new ArrayList<>();
                    dataNodeSuffix.stream().forEach(item -> {
                        //表缓存中未包含这个表,则创建
                        if (!tableNameCache.contains(item)) {
                            notExistTables.add(item);
                        }
                    });
                    //如果有不存在的表，创建
                    if (!CollectionUtils.isEmpty(notExistTables)) {
                        // todo 创建表
                        notExistTables.stream().forEach(item -> {
                            //创建表
                            createNewTable(name, item);
                        });
                    }
                }
                actualDataNodesField.set(tableRule, newDataNodes);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("初始化 动态表单失败" + e.getMessage());
        }
    }

    /**
     * 创建真实表
     *
     * @param logicTableName 逻辑表名
     * @param realTableName  真实表名
     */
    public void createNewTable(String logicTableName, String realTableName) {
        // 缓存中无此表 建表
        CreateTableSql createTableSql = commonMapper.selectTableCreateSql(logicTableName);
        logger.info(JSON.toJSONString(createTableSql));
        String sql = createTableSql.getCreateTable();
        sql = sql.replace("CREATE TABLE", "CREATE TABLE IF NOT EXISTS");
        sql = sql.replace(logicTableName, realTableName);
        commonMapper.executeSql(sql);
    }


    /**
     * 缓存重载方法
     *
     * @param schemaName 待加载表名所属数据库名
     */
    public static void tableNameCacheReload(String schemaName) {
        // 读取数据库中所有表名
        List<String> tableNameList = commonMapper.getAllTableNameBySchema(schemaName);
        // 删除旧的缓存(如果存在)
        DynamicTablesHandler.tableNameCache.clear();
        // 写入新的缓存
        DynamicTablesHandler.tableNameCache.addAll(tableNameList);
    }
}
