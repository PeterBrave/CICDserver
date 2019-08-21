package org.citrix.controller;

import org.citrix.bean.RespBean;
import org.citrix.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kavin
 * @date 2019-07-10 17:51
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private HrService hrService;
    @PostMapping
    public RespBean registerUser(@RequestParam("username") String username,
                               @RequestParam("password") String password) {
        int result = hrService.hrReg(username, password);
        if (result == 1) {
            return RespBean.ok("Register Successful!");
        } else if (result == -1){
            return RespBean.error("The username already exists!");
        } else {
            return RespBean.error("Register Failed!");
        }
    }
}
