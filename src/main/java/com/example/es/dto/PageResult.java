package com.example.es.dto;

import lombok.Builder;
import lombok.Data;

/**
 * User: Leehao
 * Date: 2022/2/14
 * Time: 15:32
 * Description:
 */
@Data
@Builder
public class PageResult {

    private Object data;

    private Integer pageSize;

    private long total;

    public PageResult(Object data, Integer pageSize, long total) {
        this.data = data;
        this.pageSize = pageSize;
        this.total = total;
    }
}
