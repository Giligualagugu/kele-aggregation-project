package com.kele.jpa.demo.repository;

import com.kele.jpa.demo.entity.GsCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GsCompanyRepository extends JpaRepository<GsCompanyEntity, Integer> {
}
