package com.library.spring.controllers;

import com.library.spring.models.Reader;
import com.library.spring.repository.ReaderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reader")
public class ReaderController {
    private final ReaderRepository readerRepository;

    public ReaderController(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @GetMapping("/add")
    public String addReader(Model model){

        model.addAttribute("reader", new Reader());
        return "reader/add";
    }

    @PostMapping("/add")
    public String addReader(Reader reader){
        readerRepository.save(reader);
        return "main/main";
    }

    @GetMapping("/table")
    public String table(Model model){

        model.addAttribute("readers", readerRepository.findAll());

        return "reader/table";
    }

    @GetMapping("/{id}")
    public String reader(@PathVariable String id, Model model){

        Reader reader = readerRepository.findById(id).get();

        model.addAttribute("reader", reader);
        model.addAttribute("owed", reader.getBlacklists());

        return "reader/reader";
    }

}
