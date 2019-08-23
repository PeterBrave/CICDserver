package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.Hr;
import org.citrix.bean.RespBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
@Rollback
public class UserControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    @Autowired
    private UserController userController;

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "hrService")
    @Test
    public void getUserInfo(){
        RespBean respBean = userController.getUserInfo();
        Assert.assertNotNull(respBean);
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "hrService")
    @Test
    public void modifyUserInfo() {
        RespBean respBean = userController.getUserInfo();
        Hr hr = (Hr)respBean.getObj();
        RespBean respBean1 = userController.modifyUserInfo(hr.getName(), hr.getGithubName(), hr.getGithubToken(), hr.getEmail(), hr.getAddress(),hr.getPhone(),hr.getUserface());
        Assert.assertNotNull(respBean1);
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "hrService")
    @Test
    public void modifyUserInfoError1() {
        RespBean respBean = userController.getUserInfo();
        Hr hr = (Hr)respBean.getObj();
        RespBean respBean1 = userController.modifyUserInfo(hr.getName(), hr.getGithubName(), hr.getGithubToken(), hr.getEmail(), hr.getAddress(),hr.getPhone(),hr.getUserface());
        Assert.assertNotNull(respBean1);
    }

    @WithUserDetails(value = "admin", userDetailsServiceBeanName = "hrService")
    @Test
    public void modifyUserInfoError2() {
        RespBean respBean = userController.getUserInfo();
        Hr hr = (Hr)respBean.getObj();
        RespBean respBean1 = userController.modifyUserInfo(hr.getName(), hr.getGithubName(), hr.getGithubToken(), hr.getEmail(), hr.getAddress(),hr.getPhone(),hr.getUserface());
        Assert.assertNotNull(respBean1);
    }

    @Test
    public void loginTest() throws Exception{
        mockMvc
                .perform(formLogin("/login").user("admin").password("123"))
                .andExpect(authenticated());
    }

    @Test
    public void LoginTestFailure() throws Exception{
        mockMvc
                .perform(formLogin("/login").user("admin").password("invalid"))
                .andExpect(unauthenticated());
    }

    @Test
    public void LoginTestFailure2() throws Exception{
        mockMvc
                .perform(formLogin("/login").user("libai").password("123"))
                .andExpect(unauthenticated());
    }

    @Test
    public void testLogoutFail() throws Exception {
        // 测试退出登录
        mockMvc.perform(logout("/logout")).andExpect(unauthenticated());
    }

}