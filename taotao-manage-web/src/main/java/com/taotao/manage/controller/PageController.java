package com.taotao.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 通用的页面跳转控制器
 * 
 * @author itcast
 *
 */
@RequestMapping("/page")
@Controller
public class PageController {

    @RequestMapping("/{pageName}")
    public String toPage(@PathVariable("pageName") String pageName) {
        return pageName;
    }

}
