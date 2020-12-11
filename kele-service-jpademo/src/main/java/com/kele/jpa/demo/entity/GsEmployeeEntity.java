package com.kele.jpa.demo.entity;

import com.kele.jpa.demo.enums.GenderEnum;
import com.kele.jpa.demo.enums.GenderEnumConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "gs_employee_info")
public class GsEmployeeEntity extends BaseEntity {

    private static final long serialVersionUID = 7585249387350059136L;



    /**
     * 归属的公司id, 外键;  外键字段不可手动修改;
     */
    @Column(name = "belong_gs_id", nullable = false, insertable = false, updatable = false)
    private Integer belongGsId;

    private String username;

    private Integer age;

    @Convert(converter = GenderEnumConverter.class)
    private GenderEnum sex = GenderEnum.UN_KNWON;

    /**
     * 归属的公司实体,  非真实数据库 field;
     * <p>
     * 多个员工对应一个 公司 多对一
     */
    @ManyToOne(targetEntity = GsCompanyEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "belong_gs_id", referencedColumnName = "id")
    private GsCompanyEntity gsCompanyEntity;
}
