package com.iba.springbootdeveloper.controller;

import com.iba.springbootdeveloper.dto.AddUserRequest;
import com.iba.springbootdeveloper.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "redirect:/about";
    }

    @GetMapping("/promoteUser")
    public String showPromoteForm() {
        return "promoteUser"; // 위의 HTML 파일의 이름과 일치해야 합니다
    }
}
