package org.citrix.controller;

import lombok.extern.slf4j.Slf4j;
import org.citrix.service.HrService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class RegisterControllerTest {

    @Autowired
    private HrService hrService;
    @Test
    public void registerUser() {
        int result = hrService.hrReg("tester", "123");
        Assert.assertNotNull(result);
    }
}