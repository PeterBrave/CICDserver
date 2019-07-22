package org.sang.controller;

import org.sang.bean.RespBean;
import org.sang.service.HrService;
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
        hrService.hrReg(username, password);
        return RespBean.ok("添加成功!");
    }

}
