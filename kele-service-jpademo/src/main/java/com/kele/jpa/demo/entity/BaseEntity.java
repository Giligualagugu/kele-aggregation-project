package com.kele.jpa.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 需要开启 @EnableJpaAuditing 注解; 然后@CreatedDate @LastModifiedDate生效
 */
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = -7882101870214310257L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedDate
    @Column
    private LocalDateTime createTime;

    @LastModifiedDate
    @Column
    private LocalDateTime updateTime;

}
