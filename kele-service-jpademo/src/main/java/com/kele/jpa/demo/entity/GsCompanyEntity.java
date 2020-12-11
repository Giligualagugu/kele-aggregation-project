package com.kele.jpa.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * 不要使用 @Data注解
 */
@Entity
@Table(name = "gs_company_info")
@Getter
@Setter
public class GsCompanyEntity extends BaseEntity {

    private static final long serialVersionUID = -4594763644308553549L;



    @Column
    private String gsName;

    @Column(unique = true)// 税号是唯一约束
    private String taxCode;

    @Column(scale = 2, precision = 10) // 对应数据库 decimal(10,2) 类型;
    private BigDecimal registerMoney;


    /**
     * 公司包含多个员工, 使用set集合,非真实数据库field;
     * <p>
     * 1.声明关系
     * 2.配置关系(外键) // 主表不要维护  从表来维护就可以了; 只单向维护;
     */
//    @OneToMany(targetEntity = GsEmployeeEntity.class)
//    @JoinColumn(name = "belong_gs_id", referencedColumnName = "id")// name是外键名称 referencedColumnName是外键指向的 键名称

    @OneToMany(mappedBy = "gsCompanyEntity", cascade = CascadeType.ALL)// 放弃从主表维护外键,只声明关系;
    private Set<GsEmployeeEntity> employeeEntitySet = new HashSet<>();


}
