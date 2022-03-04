package com.example.es.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Leehao
 * Date: 2022/3/4
 * Time: 11:21
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTableSql {

    private String table;

    private String createTable;

}
