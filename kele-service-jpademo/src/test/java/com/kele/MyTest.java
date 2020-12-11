package com.kele;


import com.kele.jpa.demo.DemoJpaApp;
import com.kele.jpa.demo.GsCompanyEntity;
import com.kele.jpa.demo.repository.GsCompanyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest(classes = DemoJpaApp.class)
@RunWith(SpringRunner.class)
public class MyTest {

    @Autowired
    GsCompanyRepository gsCompanyRepository;

    @Test
    public void  tset1(){

        Optional<GsCompanyEntity> byId = gsCompanyRepository.findById(1);

        System.out.println(byId.orElse(null));

    }
}
