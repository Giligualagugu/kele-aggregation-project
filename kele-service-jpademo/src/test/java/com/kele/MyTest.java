package com.kele;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.kele.jpa.demo.DemoJpaApp;
import com.kele.jpa.demo.dto.GsCompanyDTO;
import com.kele.jpa.demo.entity.GsCompanyEntity;
import com.kele.jpa.demo.entity.GsEmployeeEntity;
import com.kele.jpa.demo.repository.GsCompanyRepository;
import com.kele.jpa.demo.repository.GsEmployeeEntityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest(classes = DemoJpaApp.class)
@RunWith(SpringRunner.class)
public class MyTest {

    @Autowired
    GsCompanyRepository gsCompanyRepository;

    @Autowired
    GsEmployeeEntityRepository employeeEntityRepository;

    @Transactional
    @Rollback(false)
    @Test
    public void tset1() {
        GsCompanyEntity entity = new GsCompanyEntity();
        entity.setGsName("kele");
        entity.setTaxCode("tax" + RandomUtil.randomNumbers(6));
        entity.setRegisterMoney(BigDecimal.valueOf(200000L));

        GsEmployeeEntity employeeEntity = new GsEmployeeEntity();
        employeeEntity.setAge(12);
        employeeEntity.setUsername("jack");
        employeeEntity.setGsCompanyEntity(entity); // 主表放弃维护 外键关系后, 从表一定要维护 不然从表外键字段为null;

        GsEmployeeEntity employeeEntity2 = new GsEmployeeEntity();
        employeeEntity2.setAge(13);
        employeeEntity2.setUsername("jack2");
        employeeEntity2.setGsCompanyEntity(entity);
        entity.getEmployeeEntitySet().addAll(CollectionUtil.newHashSet(employeeEntity, employeeEntity2));

        gsCompanyRepository.save(entity);

    }


    @Test
    public void test3() {

        List<GsCompanyEntity> all = gsCompanyRepository.findAll();

        List<GsCompanyDTO> collect = all.stream().map(GsCompanyDTO::convertFromEntity).collect(Collectors.toList());

        System.out.println(JSON.toJSONString(collect, true));

    }

    @Transactional
    @Rollback(false)
    @Test
    public void test4() {

        Optional<GsEmployeeEntity> byId = employeeEntityRepository.findById(1);
        GsEmployeeEntity gsEmployeeEntity = byId.get();
        gsEmployeeEntity.setUsername("tom233");
        employeeEntityRepository.save(gsEmployeeEntity);

    }


}
