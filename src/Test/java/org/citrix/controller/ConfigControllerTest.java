package org.citrix.controller;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.Hr;
import org.citrix.bean.Menu;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class ConfigControllerTest {

    @Autowired
    private ConfigController configController;

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "hrService")
    @Test
    public void sysmenu() {
        List<Menu> list = configController.sysmenu();
        Assert.assertNotNull(list);
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "hrService")
    @Test
    public void currentUser() {
        Hr hr = configController.currentUser();
        Assert.assertNotNull(hr);
    }
}