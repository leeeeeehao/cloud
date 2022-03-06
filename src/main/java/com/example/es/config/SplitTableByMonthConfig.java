package com.example.es.config;

import com.example.es.entity.DynamicMonthTableEntity;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Data
@Component
@ToString
@ConfigurationProperties(prefix = "split.by.month")
public class SplitTableByMonthConfig {

    /*
     * key 表名  value 分表参数
     * */
    private HashMap<String, DynamicMonthTableEntity> tables;

}
