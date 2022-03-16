package com.example.es.controller;

import com.example.es.entity.TbScore;
import com.example.es.handler.DynamicTablesHandler;
import com.example.es.mapper.TbScoreMapper;
import com.example.es.vo.AcrossDbSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void actualTablesRefreshTest() {
        try {
            dynamicTablesHandler.CreateWcTableJobHandler();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/selectCount")
    public void selectCount() {
        tbScoreMapper.count();
    }

    /**
     * 分表测试
     * @param tbScore
     */
    @PostMapping("/shardingInsert")
    public void shardingInsert(@RequestBody TbScore tbScore) {
        tbScoreMapper.addUser(tbScore);
    }

    /**
     * 跨库查询测试
     * @return
     */
    @PostMapping("/acrossDbSelect")
    public List<AcrossDbSelect> acrossDbSelect(){
        return tbScoreMapper.acrossDbSelect();
    }

    /**
     * 子查询测试
     * @return
     */
    @GetMapping("/subQuerySelect")
    public List<Integer> subQuerySelect(){
        return tbScoreMapper.subQuerySelect();
    }

}
