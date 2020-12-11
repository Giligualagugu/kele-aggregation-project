package com.kele.jpa.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "gs_address_info")
@Getter
@Setter
public class GsAddressEntity extends BaseEntity {
    
    private static final long serialVersionUID = 7765368638804446281L;

    @Column(name = "gs_id", updatable = false, insertable = false)
    private Integer gsId;

    private String country;

    private String province;

    private String city;

    private String area;

    @OneToOne(targetEntity = GsCompanyEntity.class)
    @JoinColumn(name = "gs_id", referencedColumnName = "id")
    private GsCompanyEntity companyEntity;

}
