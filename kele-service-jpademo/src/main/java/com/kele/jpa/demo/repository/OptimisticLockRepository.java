package com.kele.jpa.demo.repository;

import com.kele.jpa.demo.entity.OptimisticLockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptimisticLockRepository extends JpaRepository<OptimisticLockEntity,Integer> {
}
