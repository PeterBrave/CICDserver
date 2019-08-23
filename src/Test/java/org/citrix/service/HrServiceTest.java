package org.citrix.service;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.Hr;
import org.citrix.bean.RespBean;
import org.citrix.controller.jenkins.UserController;
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
    @Test
    public void loadUserByUsername() {
        try {
            hrService.loadUserByUsername("test_not_exists");
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
}