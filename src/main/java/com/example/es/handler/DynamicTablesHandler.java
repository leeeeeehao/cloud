package com.example.es.handler;

import com.example.es.config.DynamicTablesProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.exception.ShardingConfigurationException;
import org.apache.shardingsphere.core.rule.DataNode;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.shardingjdbc.jdbc.core.datasource.ShardingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Leehao
 * Date: 2022/3/4
 * Time: 16:50
 * Description:
 */
@Slf4j
@Component
@EnableConfigurationProperties(DynamicTablesProperties.class)
public class DynamicTablesHandler {

    @Autowired
    private DynamicTablesProperties dynamicTablesProperties;

    @Autowired
    private DataSource dataSource;

    /***
     * 启动的时候初始化一下 动态表单的配置    初化当前年
     */
    @PostConstruct
    private  void intData(){
        Date date =new Date();
        SimpleDateFormat format =  new SimpleDateFormat("yyyy");
        String s =  format.format(date);
        actualTablesRefresh(Integer.parseInt(s));
    }

    /**
     *  动态更新 处理化的表配置
     * @param data   表名称
     */
    public void actualTablesRefresh(Integer data)  {
        try {
            System.out.println("---------------------------------");
            ShardingDataSource dataSource = (ShardingDataSource) this.dataSource;
            if (dynamicTablesProperties.getNames() == null || dynamicTablesProperties.getNames().length == 0) {
                log.info("dynamic.table.names为空");
                return;
            }
            for (int i = 0; i < dynamicTablesProperties.getNames().length; i++) {
                TableRule tableRule = null;
                try {
                    tableRule = dataSource.getShardingContext().getShardingRule().getTableRule(dynamicTablesProperties.getNames()[i]);
                } catch (ShardingConfigurationException e) {
                    log.error("报错啦" +   e.getMessage());

                }
                List<DataNode> dataNodes = tableRule.getActualDataNodes();
                Field actualDataNodesField = TableRule.class.getDeclaredField("actualDataNodes");
                Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(actualDataNodesField, actualDataNodesField.getModifiers() & ~Modifier.FINAL);
                actualDataNodesField.setAccessible(true);
                List<DataNode> newDataNodes = new ArrayList<>();
                int time = dynamicTablesProperties.getStartYear();
                String dataSourceName = dataNodes.get(0).getDataSourceName();
                while (true) {
                    DataNode dataNode = new DataNode(dataSourceName + "." + dynamicTablesProperties.getNames()[i]+ "_" + time);
                    newDataNodes.add(dataNode);
                    time = time + 1;
                    if (time > data.intValue()) {
                        break;
                    }
                }
                actualDataNodesField.set(tableRule, newDataNodes);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("初始化 动态表单失败" + e.getMessage());
        }
    }

}
