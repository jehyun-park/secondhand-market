package com.example.begin.controller;


import com.example.begin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller

public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String home() {

        return "home";
    }}




