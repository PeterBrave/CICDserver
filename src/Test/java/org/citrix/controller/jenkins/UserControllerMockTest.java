package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.RespBean;
import org.citrix.service.HrService;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserControllerMockTest {

    @Autowired
    public UserController userController;

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "hrService")
    @Test
    public void modifyUserInfo() {
        HrService mock = EasyMock.createMock(HrService.class);
        EasyMock.expect(mock.updateHr(EasyMock.anyObject())).andReturn(-1);
        EasyMock.replay(mock);
        userController.setHrService(mock);

        RespBean respBean = userController.modifyUserInfo("testProject", "tester", "Java", "123@qq.com", "123", "12","");

        int result = respBean.getStatus();
        Assert.assertEquals(500, result);
        EasyMock.verify(mock);
    }
}