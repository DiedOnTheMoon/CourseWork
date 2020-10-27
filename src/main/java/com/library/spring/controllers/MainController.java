package com.library.spring.controllers;

import com.library.spring.models.Book;
import com.library.spring.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class MainController {
    private final BookRepository bookRepository;

    public MainController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/main")
    public String main(){
        return "/main/main";
    }


    @GetMapping("/main/table")
    public String getTable(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "main/table";
    }
}
