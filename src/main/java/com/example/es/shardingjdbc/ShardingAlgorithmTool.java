//package com.example.es.shardingjdbc;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
//
///**
// * 分表工具
// */
//@Slf4j
//public abstract class ShardingAlgorithmTool<T extends Comparable<?>> implements PreciseShardingAlgorithm<T> {
//
//
//    /**
//     * 判断 分表获取的表名是否存在 不存在则自动建表
//     *
//     * @param logicTableName  逻辑表名(表头)
//     * @param resultTableName 真实表名
//     * @return 确认存在于数据库中的真实表名
//     */
//    public String shardingTablesCheckAndCreatAndReturn(String logicTableName, String resultTableName) {
//        return resultTableName;
//    }
//
//}
