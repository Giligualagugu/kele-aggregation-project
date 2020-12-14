package com.kele;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.kele.jpa.demo.DemoJpaApp;
import com.kele.jpa.demo.dto.GsCompanyDTO;
import com.kele.jpa.demo.entity.*;
import com.kele.jpa.demo.repository.*;
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


    /**
     * 无论是双向维护还是单向维护, 编码时 两边都必须显示声明关系 通过 setXXX方法;
     */
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

        GsAddressEntity addressEntity = new GsAddressEntity();
        addressEntity.setCountry("china");
        addressEntity.setProvince("jiangsu");
        addressEntity.setCity("nanjing");
        addressEntity.setArea("666");

        addressEntity.setCompanyEntity(entity);
        entity.setAddressEntity(addressEntity);


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

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Transactional
    @Rollback(false)
    @Test
    public void test5() {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("michael");
        userEntity.setPassword("kele1212");
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleDesc("管理员");
        userEntity.getRoleEntitySet().add(roleEntity);

        userRepository.save(userEntity);

    }

    @Autowired
    OptimisticLockRepository optimisticLockRepository;

    /**
     * 乐观锁 test
     */
    @Transactional
    @Rollback(false)
    @Test
    public void test6() {
        OptimisticLockEntity entity = new OptimisticLockEntity();
        entity.setUsername("kele");
        entity.setMoneyLeft(BigDecimal.valueOf(100L));
        optimisticLockRepository.save(entity);
    }

    /**
     * 乐观锁 test
     */
//    @Transactional
//    @Rollback(false)
    @Test
    public void test7() {

        Optional<OptimisticLockEntity> optional = optimisticLockRepository.findById(1);
        optional.ifPresent(e -> {
            e.setMoneyLeft(BigDecimal.valueOf(989));
//            e.setVersion(22); // 版本不一致 抛出 ObjectOptimisticLockingFailureException
            optimisticLockRepository.save(e);
        });

//        Optional<OptimisticLockEntity> optional2 = optimisticLockRepository.findById(1);
//        optional2.ifPresent(e -> {
//            e.setMoneyLeft(BigDecimal.valueOf(101));
//            optimisticLockRepository.save(e);
//        });


    }


}
