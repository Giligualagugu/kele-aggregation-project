package com.kele.jpa.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "gs_company_address")
@Data
public class GsAddressEntity extends BaseEntity {
    private static final long serialVersionUID = 7765368638804446281L;


    private String country;

    private String province;

    private String city;

    private String area;
}
