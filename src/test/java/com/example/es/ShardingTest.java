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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    void insertTest() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = "2022-01-14 00:00:00";

        try {
            Date date = format.parse(dateStr);

            TbScore tbScore = TbScore
                    .builder()
                    .userId("213")
                    .score(51)
                    .subject("数学")
                    .createTime(date)
                    .build();
            tbScoreMapper.addUser(tbScore);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    void selectCount() {
        System.out.printf(tbScoreMapper.count() + "");
    }
}
