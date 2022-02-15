package com.example.es;

import com.alibaba.fastjson.JSON;
import com.example.es.entity.BasePage;
import com.example.es.entity.New;
import com.example.es.service.INewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @Test
    void batchInsert() {
        List<New> newList = new ArrayList<>();
        New new1 = New
                .builder()
                .id(UUID.randomUUID().toString())
                .newTitle("吃着韭菜盒子 谷爱凌闯进明日决赛")
                .newContent("由于要备战明天的决赛，谷爱凌还要进行训练。这一次她没有太多时间去接受采访，匆匆走过混合采访区时，有人询问她刚才吃的是什么？谷爱凌说了四个字——“韭菜盒子”。对于外国朋友，甚至是一些南方朋友，可能对于“韭菜盒子”感到陌生，需要解释一番才能明白。对于北京人来说，韭菜盒子是一道最常见的家常美食，几乎所有人都吃过，说着一口的京腔，吃着韭菜盒子，谷爱凌真是地道的“北京大妞”。")
                .newState(1)
                .newType(1)
                .createTime(new Date())
                .build();
        newList.add(new1);
        New new2 = New
                .builder()
                .id(UUID.randomUUID().toString())
                .newTitle("三部委公布第二轮“双一流”建设高校及建设学科名单")
                .newContent("中国青年报客户端北京2月14日电（中青报·中青网记者 叶雨婷）记者今天从教育部获悉，经专家委员会认定，教育部、财政部、国家发展改革委研究并报国务院批准，第二轮“双一流”建设高校及建设学科名单和给予公开警示的首轮建设学科名单公布。")
                .newState(1)
                .newType(1)
                .createTime(new Date())
                .build();
        newList.add(new2);
        iNewService.bulkInsert(newList);
    }

    @Test
    void checkIndex() {
        System.out.printf(iNewService.checkIndex("news") + "");
    }

    @Test
    void page() {
        System.out.printf(JSON.toJSONString(iNewService.getPage(BasePage.builder().page(1).size(2).build(), "news", New.builder().newTitle("新闻").build())));
    }

}
