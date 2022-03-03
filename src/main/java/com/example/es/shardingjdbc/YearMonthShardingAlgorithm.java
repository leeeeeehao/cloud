package com.example.es.shardingjdbc;

import cn.hutool.core.date.DateUtil;
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
public class YearMonthShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Date> shardingValue) {
        String tbName = shardingValue.getLogicTableName() + "_" + DateUtil.format(shardingValue.getValue(),"yyyy_MM");
        System.out.println("Sharding input:" + shardingValue.getValue() + ", output:{}" + tbName);
        return tbName;
    }
}
