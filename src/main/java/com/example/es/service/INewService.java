package com.example.es.service;

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

    void insert(New n);

    Integer bulkInsert(List<New> news);

}
