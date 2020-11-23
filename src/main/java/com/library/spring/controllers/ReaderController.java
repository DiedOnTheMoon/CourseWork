package com.library.spring.controllers;

import com.library.spring.models.Blacklist;
import com.library.spring.models.Reader;
import com.library.spring.models.SpecificBook;
import com.library.spring.repository.ReaderRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reader")
public class ReaderController {
    private String blacklistFilter;
    private String bookFilter;
    private final ReaderRepository readerRepository;

    public ReaderController(ReaderRepository readerRepository) {
        this.readerRepository = readerRepository;
    }

    @GetMapping("/add")
    public String addReader(@ModelAttribute("reader") Reader reader){
        return "reader/add";
    }

    @PostMapping("/add")
    public String addReader(
            @ModelAttribute("reader") @Valid Reader reader,
            BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "reader/add";
        }

        readerRepository.save(reader);

        return "redirect:reader/table/";
    }

    @GetMapping("/table")
    public String table(Model model){

        model.addAttribute("readers", readerRepository.findAll());

        return "reader/table";
    }

    @PostMapping("/filter/table")
    public String tableFilter(@RequestParam(defaultValue = "") String filter, Model model){
        model.addAttribute("readers", readerRepository.findAll().stream()
                .filter(r -> r.getLastName().startsWith(filter)));
        return "reader/table";
    }

    @GetMapping("/{id}")
    public String reader(@PathVariable("id") Long id, Model model){

        Reader reader = readerRepository.findById(id).get();

        model.addAttribute("reader", reader);
        model.addAttribute("books", reader.getSpecificBooks().stream()
                .filter(b -> b.getBlacklist() == null)
                .collect(Collectors.toSet()));
        model.addAttribute("owed", reader.getBlacklists());

        return "reader/reader";
    }

    @PostMapping("/filter/blacklist")
    public String blacklistFilter(@RequestParam(defaultValue = "") String blacklistFilter,
                         @RequestParam Long id,
                         Model model){
        Reader reader =  readerRepository.findById(id).get();
        Set<Blacklist> blacklists;
        Set<SpecificBook> books;

        if(blacklistFilter != null && !blacklistFilter.isBlank())
        {
            blacklists = reader.getBlacklists().stream()
                    .filter( b -> b.getSpecificBook().getBook().getBookName().startsWith(blacklistFilter))
                    .collect(Collectors.toSet());
            this.blacklistFilter = blacklistFilter;
        }else{
            blacklists = reader.getBlacklists();
            this.blacklistFilter = "";
        }

        if(bookFilter != null && !bookFilter.isBlank()){
            books = reader.getSpecificBooks().stream()
                    .filter(b -> b.getBook().getBookName().startsWith(bookFilter))
                    .collect(Collectors.toSet());
        }else {
            books = reader.getSpecificBooks();
        }

        model.addAttribute("reader", reader);
        model.addAttribute("owed", blacklists);
        model.addAttribute("books", books);

        return "reader/reader";
    }

    @PostMapping("/filter/book")
    public String bookFilter(@RequestParam(defaultValue = "") String bookFilter,
                         @RequestParam Long id,
                         Model model){
        Reader reader =  readerRepository.findById(id).get();
        Set<Blacklist> blacklists;
        Set<SpecificBook> books;

        if(blacklistFilter != null && !blacklistFilter.isBlank())
        {
            blacklists = reader.getBlacklists().stream()
                    .filter( b -> b.getSpecificBook().getBook().getBookName().startsWith(blacklistFilter))
                    .collect(Collectors.toSet());
        }else{
            blacklists = reader.getBlacklists();
        }

        if(bookFilter != null && !bookFilter.isBlank()){
            books = reader.getSpecificBooks().stream()
                    .filter(b -> b.getBook().getBookName().startsWith(bookFilter))
                    .collect(Collectors.toSet());
            this.bookFilter = bookFilter;
        }else {
            books = reader.getSpecificBooks();
            this.bookFilter = "";
        }

        model.addAttribute("reader", reader);
        model.addAttribute("owed", blacklists);
        model.addAttribute("books", books);

        return "reader/reader";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){

        model.addAttribute("reader", readerRepository.findById(id).get());

        return "reader/edit";
    }

}
