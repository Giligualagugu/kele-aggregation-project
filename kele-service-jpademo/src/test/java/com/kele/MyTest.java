package com.kele;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.kele.jpa.demo.DemoJpaApp;
import com.kele.jpa.demo.entity.GsCompanyEntity;
import com.kele.jpa.demo.repository.GsCompanyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest(classes = DemoJpaApp.class)
@RunWith(SpringRunner.class)
public class MyTest {

    @Autowired
    GsCompanyRepository gsCompanyRepository;

    @Test
    public void tset1() {

        GsCompanyEntity entity = new GsCompanyEntity();
        entity.setGsName("kele");
        entity.setTaxCode(RandomUtil.randomString(10));
        entity.setRegisterMoney(BigDecimal.valueOf(200000L));
        GsCompanyEntity save = gsCompanyRepository.save(entity);

        System.out.println(JSON.toJSONString(save,true));

    }
}
