package com.example.es;

import com.alibaba.fastjson.JSON;
import com.example.es.entity.BasePage;
import com.example.es.entity.New;
import com.example.es.entity.TbScore;
import com.example.es.mapper.TbScoreMapper;
import com.example.es.service.INewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class ShardingTest {

    @Autowired
    private TbScoreMapper tbScoreMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void insertTest(){
        TbScore tbScore = TbScore
                .builder()
                .userId("213")
                .score(51)
                .subject("数学")
                .build();
        tbScoreMapper.addUser(tbScore);
    }

}
