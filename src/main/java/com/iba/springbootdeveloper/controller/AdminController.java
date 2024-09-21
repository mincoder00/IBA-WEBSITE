package com.iba.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    @GetMapping("/adminLogin")
    public String about() {
        return "adminLogin";
    }

    @PostMapping("/adminlogin")
    public String adminLogin(@RequestParam("username") String email,
                             @RequestParam("password") String password) {
        if ("pnuiba514@gmail.com".equals(email) && "ruddud2531!".equals(password)) {
            return "redirect:/promoteUser";
        } else {
            return "redirect:/adminLogin?error";
        }
    }
}
