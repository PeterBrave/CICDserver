package org.citrix.controller;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.RespBean;
import org.citrix.service.HrService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
@Rollback
public class RegisterControllerMockTest {

    @Autowired
    private RegisterController registerController;

    @Test
    public void registerUserMock() {
        HrService mock = EasyMock.createMock(HrService.class);
        EasyMock.expect(mock.hrReg("test", "tester")).andReturn(-2);
        EasyMock.replay(mock);
        registerController.setHrService(mock);
        RespBean respBean = registerController.registerUser("test", "tester");
        int result = respBean.getStatus();
        Assert.assertEquals(500, result);
        EasyMock.verify(mock);
    }
}