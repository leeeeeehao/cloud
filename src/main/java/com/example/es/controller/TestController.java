package com.example.es.controller;

import com.example.es.handler.DynamicTablesHandler;
import com.example.es.mapper.TbScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: Leehao
 * Date: 2022/3/15
 * Time: 14:46
 * Description:
 */
@RequestMapping("test")
@RestController
public class TestController {

    @Autowired
    private DynamicTablesHandler dynamicTablesHandler;

    @Autowired
    private TbScoreMapper tbScoreMapper;

    @GetMapping("/actualTablesRefresh")
    public void actualTablesRefreshTest(){
        try {
            dynamicTablesHandler.CreateWcTableJobHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/selectCount")
    public void selectCount(){
        tbScoreMapper.count();
    }

}
