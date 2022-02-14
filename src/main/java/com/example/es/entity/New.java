package com.example.es.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * User: Leehao
 * Date: 2022/2/14
 * Time: 10:39
 * Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class New implements Serializable {

    private static final long serialVersionUID = 1423747698314249829L;

    private String id;

    private String newTitle;

    private Integer newType;

    private String newContent;

    private Date createTime;

    private Integer newState;

}
