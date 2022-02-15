package com.example.es.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.es.config.RestClientConfig;
import com.example.es.dto.PageResult;
import com.example.es.entity.BasePage;
import com.example.es.entity.New;
import com.example.es.service.INewService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Leehao
 * Date: 2022/2/14
 * Time: 11:09
 * Description:
 */
@Service
public class NewServiceImpl implements INewService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public List<New> getNews(New n) {
        List<New> news = new ArrayList<>();
        //创建 Bool 查询构建器
        BoolQueryBuilder builder = new BoolQueryBuilder();
        if (StringUtils.hasLength(n.getNewContent())) {
            builder.must(QueryBuilders.matchQuery("newContent", n.getNewContent()));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(builder);
        // 创建查询请求对象，将查询对象配置到其中
        SearchRequest searchRequest = new SearchRequest("news");
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(searchRequest, RestClientConfig.COMMON_OPTIONS);
            if (RestStatus.OK.equals(response.status())) {
                SearchHits hits = response.getHits();
                for (SearchHit item : hits) {
                    New aNew = new New();
                    aNew = JSON.parseObject(JSON.toJSONString(item.getSourceAsMap()), new TypeReference<New>() {
                    });
                    aNew.setId(item.getId());
                    news.add(aNew);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return news;
    }

    @Override
    public PageResult getPage(BasePage page, String index, New n) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .from((page.getPage() - 1) * page.getPage())
                .size(page.getSize())
                .trackTotalHits(true);
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (null != n.getNewTitle()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("newTitle", n.getNewTitle()));
        }
        if (null != n.getNewContent()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("newContent", n.getNewContent()));
        }
        SearchResponse searchResponse = pageQuerySearchResponse(searchSourceBuilder, index);
        List<New> news = new ArrayList<>();
        long total = searchResponse.getHits().getTotalHits().value;
        Integer pageSize = page.getSize();
        SearchHits hits = searchResponse.getHits();
        for (SearchHit item : hits) {
            New aNew = new New();
            aNew = JSON.parseObject(JSON.toJSONString(item.getSourceAsMap()), new TypeReference<New>() {
            });
            aNew.setId(item.getId());
            news.add(aNew);
        }
        return PageResult
                .builder()
                .total(total)
                .pageSize(pageSize)
                .data(news)
                .build();
    }

    /**
     * 分页查询搜索es
     *
     * @param searchSourceBuilder
     * @param index
     * @return
     */
    private SearchResponse pageQuerySearchResponse(SearchSourceBuilder searchSourceBuilder, String index) {
        SearchRequest searchRequest = new SearchRequest()
                .source(searchSourceBuilder)
                .indices(index);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RestClientConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResponse;
    }

    @Override
    public void insert(New n) {
        IndexRequest indexRequest = new IndexRequest("news");
        String source = JSON.toJSONString(n);
        try {
            indexRequest.source(source, XContentType.JSON);
            IndexResponse res = restHighLevelClient.index(indexRequest, RestClientConfig.COMMON_OPTIONS);
            System.out.printf("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer bulkInsert(List<New> news) {
        BulkRequest bulkRequest = new BulkRequest();
        news.stream().forEach(item -> {
            String source = JSON.toJSONString(item);
            bulkRequest.add(new IndexRequest("news").source(source, XContentType.JSON));
        });
        addBatch(bulkRequest);
        return null;
    }

    @Override
    public boolean checkIndex(String index) {
        GetIndexRequest request = new GetIndexRequest(index);
        try {
            return restHighLevelClient.indices().exists(request, RestClientConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void addBatch(BulkRequest bulkRequest) {
        try {
            restHighLevelClient.bulk(bulkRequest, RestClientConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
