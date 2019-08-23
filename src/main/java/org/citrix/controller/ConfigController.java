package org.citrix.controller;

import org.citrix.bean.Hr;
import org.citrix.bean.Menu;
import org.citrix.common.HrUtils;
import org.citrix.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 这是一个只要登录就能访问的Controller
 * 主要用来获取一些配置信息
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    MenuService menuService;
    @GetMapping("/sysmenu")
    public List<Menu> sysmenu() {
        return menuService.getMenusByHrId();
    }

    @GetMapping("/hr")
    public Hr currentUser() {
        return HrUtils.getCurrentHr();
    }
}
