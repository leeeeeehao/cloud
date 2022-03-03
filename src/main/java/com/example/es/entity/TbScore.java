package com.example.es.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User: Leehao
 * Date: 2022/3/3
 * Time: 17:03
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TbScore {

    private Integer id;

    private String userId;

    private String subject;

    private Integer score;

}
