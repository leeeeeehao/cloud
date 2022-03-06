package com.example.es.shardingjdbc;

import cn.hutool.core.date.DateUtil;
import com.example.es.util.ShardingUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
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
public class YearMonthShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

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
        throw new IllegalArgumentException("未找到匹配的数据表");
    }

}
