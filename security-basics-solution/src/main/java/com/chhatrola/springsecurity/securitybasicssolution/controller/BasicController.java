package com.chhatrola.springsecurity.securitybasicssolution.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by niv214 on 12/5/20.
 */
@RestController
public class BasicController {

    @GetMapping("/hello")
    public String doOps(){
        return "Hello Security World";
    }

}
