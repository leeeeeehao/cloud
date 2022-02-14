package com.example.es;

import com.alibaba.fastjson.JSON;
import com.example.es.entity.New;
import com.example.es.service.INewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
class EsApplicationTests {

    @Autowired
    private INewService iNewService;

    @Test
    void contextLoads() {
    }

    @Test
    void getNews() {
        System.out.printf(JSON.toJSONString(iNewService.getNews(New
                .builder()
                .newContent("央视新闻客户端")
                .build())));
    }

    @Test
    void insertNew() {
        iNewService.insert(New
                .builder()
                .id(UUID.randomUUID().toString())
                .newTitle("学习关键词丨冰雪为媒 共赴冬奥之约")
                .newContent("“成功举办北京冬奥会、冬残奥会，不仅可以增强我们实现中华民族伟大复兴的信心，而且有利于展示我们国家和民族致力于推动构建人类命运共同体，阳光、富强、开放的良好形象，增进各国人民对中国的了解和认识。”")
                .newType(1)
                .newState(1)
                .createTime(new Date())
                .build());
    }

}
