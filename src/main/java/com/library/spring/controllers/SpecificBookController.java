package com.library.spring.controllers;

import com.library.spring.models.SpecificBook;
import com.library.spring.repository.BookRepository;
import com.library.spring.repository.SpecificBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/specificBook")
public class SpecificBookController {
    private final BookRepository bookRepository;
    private final SpecificBookRepository specificBookRepository;

    public SpecificBookController(BookRepository bookRepository, SpecificBookRepository specificBookRepository) {
        this.bookRepository = bookRepository;
        this.specificBookRepository = specificBookRepository;
    }

    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable String id, Model model){
        model.addAttribute("book", specificBookRepository.findById(Long.parseLong(id)).get());
        return "specificBook/edit";
    }

    @PostMapping("/edit")
    public String saveEdit(SpecificBook book){
        specificBookRepository.save(book);
        return "/main/main";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBookById(@PathVariable String id){
        specificBookRepository.deleteById(Long.parseLong(id));
        return "main/main";
    }



}