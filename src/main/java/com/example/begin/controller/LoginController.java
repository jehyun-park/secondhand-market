package com.example.begin.controller;

import com.example.begin.dto.UserDto;
import com.example.begin.entity.User;
import com.example.begin.repository.UserRepository;
import com.example.begin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "login";
    }


    @PostMapping(value = "/login")
    public String signIn(UserDto userDto,Model model, HttpSession session){
        var loggedInUser = userService.login(userDto);
        if(loggedInUser != null) {
            log.info("login success : {}", loggedInUser.toString());
            session.setAttribute("user", loggedInUser); // User 객체로 세션에 저장
            return "redirect:/";
        } else {
            model.addAttribute("message", "아이디를 확인해주세요.");
            model.addAttribute("searchUrl", "/login");
            return "message";
        }

    }


    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        // 세션에서 User 객체를 가져옴
        User loggedInUser = (User) session.getAttribute("user");
        // User 객체가 null이 아닐 때 (로그인 되어 있을 때)
        if (loggedInUser != null) {
            model.addAttribute("userId", loggedInUser.getUserId());
            return "home";
        }
        // User 객체가 null일 때 (로그인 되어 있지 않을 때)
        return "redirect:/login"; // 로그인 페이지로 이동
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }




}
