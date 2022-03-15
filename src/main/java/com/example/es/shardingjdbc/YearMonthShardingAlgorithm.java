package com.example.es.shardingjdbc;

import com.example.es.handler.DynamicTablesHandler;
import com.example.es.util.ShardingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.apache.shardingsphere.sharding.rule.ShardingRule;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

/**
 * User: Leehao
 * Date: 2022/3/3
 * Time: 18:17
 * Description:
 */
@Slf4j
@Component
public class YearMonthShardingAlgorithm implements StandardShardingAlgorithm<Date> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
        log.info("进入查询策略");
        return null;
    }

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        log.info("逻辑表名:{},逻辑键:{},逻辑键值{}", preciseShardingValue.getLogicTableName(), preciseShardingValue.getColumnName(), preciseShardingValue.getValue());
        Date date = preciseShardingValue.getValue();
        //获取分片键的日期格式为"2020_12"
        String tableSuffix = ShardingUtils.getSuffixByYearMonth(date);
        //匹配表
        for (String tableName : collection) {
            if (tableName.endsWith(tableSuffix)) {
                return tableName;
            }
        }
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return null;
    }
}
