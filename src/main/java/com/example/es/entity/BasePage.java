package com.example.es.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Leehao
 * Date: 2022/2/14
 * Time: 15:24
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasePage {

    private Integer page;

    private Integer size;

    private String sortField;

}
