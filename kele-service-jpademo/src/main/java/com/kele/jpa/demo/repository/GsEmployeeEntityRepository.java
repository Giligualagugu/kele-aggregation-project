package com.kele.jpa.demo.repository;

import com.kele.jpa.demo.entity.GsEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GsEmployeeEntityRepository extends JpaRepository<GsEmployeeEntity, Integer> {
}
