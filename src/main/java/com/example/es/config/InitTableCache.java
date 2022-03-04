package com.example.es.config;

import com.example.es.mapper.CommonMapper;
import com.example.es.shardingjdbc.ShardingAlgorithmTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * User: Leehao
 * Date: 2022/3/4
 * Time: 11:26
 * Description:
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(DynamicTablesProperties.class)
public class InitTableCache {

    @Value("${db.schema-name}")
    private String schemaName;

    @Autowired
    private DynamicTablesProperties dynamicTables;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CommonMapper commonMapper;

    @PostConstruct
    public void intTable() {
        // 给 分表工具类注入属性
        ShardingAlgorithmTool.setCommonMapper(commonMapper);
        ShardingAlgorithmTool.tableNameCacheReload(schemaName);
    }

//    /**
//     * 6 个小时执行一次
//     */
//    @PostConstruct
////    @Scheduled(fixedRate = 1000 * 60 * 60 * 12)
//    public void refreshActualDataNodes() throws NoSuchFieldException, IllegalAccessException {
//        log.info("Job 动态刷新 actualDataNodes START");
//        if (dynamicTables.getNames() == null || dynamicTables.getNames().length == 0) {
//            log.error("【dynamic.table.names】配置为空!");
//            return;
//        }
//        for (int i = 0; i < dynamicTables.getNames().length; i++) {
//            String dynamicTableName = dynamicTables.getNames()[i];
//            TableRule tableRule = null;
//            try {
//                ShardingDataSource shardingDataSource = (ShardingDataSource) dataSource;
//                ShardingRule shardingRule = shardingDataSource.getShardingContext().getShardingRule();
//                tableRule = shardingRule.getTableRule(dynamicTableName);
//            } catch (ShardingConfigurationException e) {
//                log.error(String.format("逻辑表：%s 动态分表配置错误！", dynamicTableName));
//            }
//            String dataSourceName = tableRule.getActualDataNodes().get(0).getDataSourceName();
//            String logicTableName = tableRule.getLogicTable();
//            assert tableRule != null;
//            List<DataNode> newDataNodes = getDataNodes(dynamicTableName, dataSourceName, logicTableName);
//            if (newDataNodes.isEmpty()) {
//                throw new UnsupportedOperationException();
//            }
////            createSubTableIfAbsent(logicTableName, newDataNodes);
////            dynamicRefreshDatasource(dataSourceName, tableRule, newDataNodes);
//        }
//        log.info("Job 动态刷新 actualDataNodes END");
//    }
//
//
//    /**
//     * 获取数据节点
//     */
//    private List<DataNode> getDataNodes(String tableName, String dataSourceName, String logicTableName) {
//        Set<DataNode> newDataNodes = Sets.newHashSet();
//        StringBuilder stringBuilder = new StringBuilder().append(dataSourceName).append(".").append(logicTableName);
//        final int length = stringBuilder.length();
////        // 根据自增id范围分表的场景
////        if (tableName.equals(MESSAGE_ID_MAPPING)) {
////            Long maxMessageId = xxxService.getMaxMessageId();
////            Long notFullSubTableSuffix = maxMessageId / MESSAGE_ID_MAPPING_SINGLE_TABLE_CAPACITY;
////            Long lastSubTableSize = maxMessageId % MESSAGE_ID_MAPPING_SINGLE_TABLE_CAPACITY;
////            if (lastSubTableSize > MESSAGE_ID_MAPPING_SINGLE_TABLE_CAPACITY >> 1) {
////                // 当最后一个分表的容量达到一半时，就扩展出一个分表
////                notFullSubTableSuffix++;
////            }
////            while (notFullSubTableSuffix >= 0L) {
////                stringBuilder.setLength(length);
////                stringBuilder.append("_").append(notFullSubTableSuffix);
////                DataNode dataNode = new DataNode(stringBuilder.toString());
////                newDataNodes.add(dataNode);
////                notFullSubTableSuffix--;
////            }
////        }
//        // 扩展点
//        return Lists.newArrayList(newDataNodes);
//    }


}
