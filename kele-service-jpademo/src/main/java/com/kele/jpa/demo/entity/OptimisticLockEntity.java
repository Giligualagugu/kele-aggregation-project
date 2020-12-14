package com.kele.jpa.demo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "optmistic_lock_info")
public class OptimisticLockEntity extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "money_left", precision = 10, scale = 2)
    private BigDecimal moneyLeft;

    @Version
    @Column(name = "version")
    private int version;


}
