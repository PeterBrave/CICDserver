package org.citrix.service;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.Hr;
import org.citrix.bean.RespBean;
import org.citrix.controller.jenkins.UserController;
import org.citrix.mapper.HrMapper;
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
@Transactional
@Rollback
public class HrServiceTest {
    @Autowired
    private UserController userController;

    @Autowired
    private HrService hrService;

    @Autowired
    private HrMapper hrMapper;

    @Test
    public void loadUserByUsername() {
        HrMapper mock = EasyMock.createMock(HrMapper.class);
        EasyMock.expect(mock.loadUserByUsername("tester")).andReturn(null);
        EasyMock.replay(mock);
        hrService.setHrMapper(mock);
        try {
            hrService.loadUserByUsername("tester");
        }catch (Exception e) {
            Assert.assertNotNull(e);
        }
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "hrService")
    @Test
    public void updateHr() {
        try {
            RespBean respBean = userController.getUserInfo();
            Hr hr = (Hr) respBean.getObj();
            hr.setId(new Long(1));
            hrService.updateHr(hr);
        }catch (Exception e){
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void hrRegMock() {
        HrMapper mock = EasyMock.createMock(HrMapper.class);
        Hr hr = new Hr();
        EasyMock.expect(mock.loadUserByUsername("tester")).andReturn(hr);
        EasyMock.replay(mock);
        hrService.setHrMapper(mock);

        int result = hrService.hrReg("tester", "123");
        Assert.assertEquals(-1, result);
        EasyMock.verify(mock);
    }

    @Test
    public void hrRegSuccess() {
         int result = hrService.hrReg("n-test", "123");
         Assert.assertEquals(1, result);
    }


}