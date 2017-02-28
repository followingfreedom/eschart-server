package com.wy.escharts.web;

import org.springframework.web.bind.annotation.*;

/**
 * Created by wangyang on 2016/12/23.
 */
@RestController
public class HomeController {

    @GetMapping(value = "/comments")
    public String home() {
        return "home";
    }
}
