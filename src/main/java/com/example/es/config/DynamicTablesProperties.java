package com.example.es.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
/**
 * User: Leehao
 * Date: 2022/3/4
 * Time: 15:17
 * Description:
 */
@ConfigurationProperties(prefix = "dynamic.table")
@Data
public class DynamicTablesProperties  {

    String[] names;

    int startYear;

}
