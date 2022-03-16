package com.example.es.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

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

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

}
