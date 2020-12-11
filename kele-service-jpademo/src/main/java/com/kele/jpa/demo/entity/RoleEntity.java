package com.kele.jpa.demo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "ad_role_info")
public class RoleEntity extends BaseEntity {
    private static final long serialVersionUID = -2928251276381469164L;

    @Column(name = "role_desc")
    private String roleDesc;


    /**
     * 多对多,  被选择的一方放弃维护权, 因为 角色是被选择的  所以role这里放弃维护权;
     */
//    @ManyToMany(targetEntity = UserEntity.class)
//    @JoinTable(name = "bind_user_role",
//            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}, // 当前 role 在中间表的外键配置
//            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")}// 对方对象 user 在中间表的外键配置
//    )

    @ManyToMany(mappedBy = "roleEntitySet")
    private Set<UserEntity> userEntitySet = new HashSet<>();

}
