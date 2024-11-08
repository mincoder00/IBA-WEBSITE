package com.iba.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GlobalController {
    @GetMapping("/")
    public String about() {
        return "about";
    }
    @GetMapping("/recruit")
    public String recruit() {
        return "recruit";
    }
    @GetMapping("/faq")
    public String faq() {return "faq";}
}
