package org.citrix.controller;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.RespBean;
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
    private RegisterController registerController;

    @Test
    public void registerUser() {
        RespBean result = registerController.registerUser("tester", "123");
        int status = result.getStatus();
        Assert.assertEquals(200, status);
    }

    @Test
    public void doubleRegisterUser() {
        registerController.registerUser("tester", "123");
        RespBean result =  registerController.registerUser("tester", "123");
        int status = result.getStatus();
        Assert.assertEquals(500, status);
    }
}