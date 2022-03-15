package com.example.es.config;

import com.example.es.handler.DynamicTablesHandler;
import com.example.es.mapper.CommonMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * User: Leehao
 * Date: 2022/3/4
 * Time: 11:26
 * Description:
 */
@Slf4j
@Configuration
public class InitTableCache {

    @Value("${db.schema-name}")
    private String schemaName;

    @Autowired
    private CommonMapper commonMapper;

    @PostConstruct
    public void intTable() {
        // 给 分表工具类注入属性
        DynamicTablesHandler.setCommonMapper(commonMapper);
        DynamicTablesHandler.tableNameCacheReload(schemaName);
    }

}
