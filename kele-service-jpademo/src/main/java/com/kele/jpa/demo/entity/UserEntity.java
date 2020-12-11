package com.kele.jpa.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ad_user_info")
public class UserEntity extends BaseEntity {
    private static final long serialVersionUID = 2451756965381375779L;


    private String username;

    private String password;

    /**
     * 一个用户有多个角色, 一个角色也对应多个用户,  多对多
     * 1.声明关系;
     * 2.声明中间关系表(包含两个外键);
     */

    @ManyToMany(targetEntity = RoleEntity.class, cascade = CascadeType.ALL)
    @JoinTable(name = "bind_user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}, // 当前UserEntity 在中间表的外键配置
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}// 对方对象 role 在中间表的外键配置
    )
    private Set<RoleEntity> roleEntitySet = new HashSet<>();

}
