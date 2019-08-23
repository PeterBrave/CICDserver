package org.citrix.controller.jenkins;

import lombok.extern.slf4j.Slf4j;
import org.citrix.bean.Hr;
import org.citrix.bean.RespBean;
import org.citrix.common.HrUtils;
import org.citrix.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author kavin
 * @date 2019-08-09 19:04
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    HrService hrService;

    @GetMapping("/info")
    public RespBean getUserInfo() {
        return RespBean.ok("success", HrUtils.getCurrentHr());
    }

    @PostMapping("/modify")
    public RespBean modifyUserInfo(@RequestParam(value = "name") String name,
                                   @RequestParam(value = "githubName") String githubName,
                                   @RequestParam(value = "githubToken") String githubToken,
                                   @RequestParam(value = "email") String email,
                                   @RequestParam(value = "address") String address,
                                   @RequestParam(value = "phone") String phone,
                                   @RequestParam(value = "userface") String userface) {
        Hr hr = HrUtils.getCurrentHr();
        hr.setName(name);
        hr.setGithubName(githubName);
        hr.setGithubToken(githubToken);
        hr.setEmail(email);
        hr.setAddress(address);
        hr.setPhone(phone);
        hr.setUserface(userface);
        int result = hrService.updateHr(hr);
        if (result == 1){
            return RespBean.ok("user information update success", HrUtils.getCurrentHr());
        }else {
            return RespBean.error("user information update error");
        }

    }
}
