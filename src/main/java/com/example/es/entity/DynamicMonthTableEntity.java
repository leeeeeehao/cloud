package com.example.es.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class DynamicMonthTableEntity {

    Integer startYear;

    Integer startMonth;

    String templateTable;

}
