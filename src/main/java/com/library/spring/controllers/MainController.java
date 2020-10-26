package com.library.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/main")
    public String main(){
        return "/main/main";
    }


    @GetMapping("/main/table")
    public String getTable(Model model){


        return "";
    }
}
