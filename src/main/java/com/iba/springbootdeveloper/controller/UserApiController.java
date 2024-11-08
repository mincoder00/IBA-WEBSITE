package com.iba.springbootdeveloper.controller;

import com.iba.springbootdeveloper.domain.User;
import com.iba.springbootdeveloper.dto.AddUserRequest;
import com.iba.springbootdeveloper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

    @PostMapping("/users/promote")
    public String promoteToManager(@RequestParam("userEmail") String userEmail) {
        userService.promoteToManager(userEmail);
        return "redirect:/promoteUser";
    }

    @GetMapping("/promoteUser")
    public String promoteUser(Model model) {
        List<User> users = userService.findAll(); // UserService에 findAll 메서드 추가 필요
        model.addAttribute("users", users);
        return "promoteUser";
    }
}
