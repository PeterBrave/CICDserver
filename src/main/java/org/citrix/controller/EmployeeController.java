package org.citrix.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by citrix on 2017/12/29.
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @RequestMapping("/basic")
    public String basic() {
        return "basic";
    }
    @RequestMapping("/")
    public String  hello() {
        return "Hello";
    }
}
