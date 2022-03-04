package com.example.es.shardingjdbc;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

/**
 * User: Leehao
 * Date: 2022/3/3
 * Time: 18:17
 * Description:
 */
@Slf4j
public class YearMonthShardingAlgorithm extends ShardingAlgorithmTool<Date> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> preciseShardingValue) {
        log.info("逻辑表名:{},逻辑键:{},逻辑键值{}", preciseShardingValue.getLogicTableName(), preciseShardingValue.getColumnName(), preciseShardingValue.getValue());
        return shardingTablesCheckAndCreatAndReturn(preciseShardingValue.getLogicTableName(), preciseShardingValue.getLogicTableName() + DateUtil.format(preciseShardingValue.getValue(), "yyyyMM"));
    }

}
