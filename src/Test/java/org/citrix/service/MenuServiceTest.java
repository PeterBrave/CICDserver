package org.citrix.service;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.Menu;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MenuServiceTest {

    @Autowired
    private MenuService menuService;
    @Test
    public void getAllMenu() {
        List<Menu> list = menuService.getAllMenu();
        Assert.assertNotNull(list);
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "hrService")
    @Test
    public void getMenusByHrId() {
        List<Menu> list = menuService.getMenusByHrId();
        Assert.assertNotNull(list);
    }
}