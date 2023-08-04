package com.example.begin.controller;

import com.example.begin.dto.UserDto;
import com.example.begin.entity.User;
import com.example.begin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "signup";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userDto") UserDto userDto, Model model) {
        UserDto savedUser = userService.registerUser(userDto);
        if (savedUser == null) {
            model.addAttribute("message", "회원가입에 실패하였습니다. 입력한 정보를 확인해주세요.");
            model.addAttribute("searchUrl", "/register");
            return "message";
        } else {
            return "redirect:/";
        }
    }
}
