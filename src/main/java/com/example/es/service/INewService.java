package com.example.es.service;

import com.example.es.dto.PageResult;
import com.example.es.entity.BasePage;
import com.example.es.entity.New;

import java.util.List;

/**
 * User: Leehao
 * Date: 2022/2/14
 * Time: 10:36
 * Description:
 */
public interface INewService {

    List<New> getNews(New n);

    PageResult getPage(BasePage page, String index, New n);

    void insert(New n);

    Integer bulkInsert(List<New> news);

    boolean checkIndex(String index);

}
