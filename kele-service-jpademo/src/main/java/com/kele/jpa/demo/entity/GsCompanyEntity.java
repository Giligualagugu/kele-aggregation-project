package com.kele.jpa.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gs_company_info")
@Data
public class GsCompanyEntity extends BaseEntity {

    private static final long serialVersionUID = -4594763644308553549L;

    @Column
    private String gsName;

    @Column(unique = true)// 税号是唯一约束
    private String taxCode;

    @Column(scale = 2, precision = 10) // 对应数据库 decimal(10,2) 类型;
    private BigDecimal registerMoney;


}
