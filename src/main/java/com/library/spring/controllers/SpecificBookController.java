package com.library.spring.controllers;

import com.library.spring.models.Book;
import com.library.spring.models.SpecificBook;
import com.library.spring.repository.BookRepository;
import com.library.spring.repository.SpecificBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/{id}/add")
    public String add(@PathVariable Long id, Model model){

        model.addAttribute("id", id);
        model.addAttribute("specificBook", new SpecificBook());

        return "specificBook/add";
    }
    //сделать
    @PostMapping("/{id}/add")
    public String addNew(@PathVariable Long id, SpecificBook specificBook, Model model){

        Book book = bookRepository.findById(id).get();
        Set<SpecificBook> books = book.getSpecificBooks();

        books.add(specificBook);
        book.setSpecificBooks(books);

        bookRepository.save(book);
        // как-то там можно
        // specificBookRepository.save(specificBook);

        return "";
    }

    @PostMapping("/{id}/filter")
    public String filter(@RequestParam(defaultValue = "") String filter,@PathVariable Long id, Model model){

        model.addAttribute("books", bookRepository.findById(id).get().getSpecificBooks().stream()
            .filter(b->b.getUniqueCode().startsWith(filter))
            .collect(Collectors.toSet()));

        return "specificBook/table";
    }

}