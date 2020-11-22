package com.library.spring.controllers;

import com.library.spring.models.Book;
import com.library.spring.models.SpecificBook;
import com.library.spring.repository.BookRepository;
import com.library.spring.repository.SpecificBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String showEdit(@PathVariable("id") Long id, Model model){
        model.addAttribute("book", specificBookRepository.findById(id).get());
        return "specificBook/edit";
    }

    @PostMapping("/edit")
    public String saveEdit(
            @ModelAttribute("book") @Valid SpecificBook book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return  "specificBook/edit";
        }

        specificBookRepository.save(book);

        return "redirect:/book/" + book.getBook().getId();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBookById(@PathVariable("id") Long id){
        specificBookRepository.deleteById(id);
        return "book/table";
    }

    @GetMapping("/{id}/add")
    public String add(
            @ModelAttribute("id") @PathVariable("id") Long id,
            @ModelAttribute("specificBook") SpecificBook specificBook){

        return "specificBook/add";
    }

    @PostMapping("/{id}/add")
    public String addNew(
            @ModelAttribute("id") @PathVariable("id") Long id,
            @ModelAttribute("specificBook") @Valid SpecificBook specificBook,
            BindingResult bindingResult
            ){
        if(bindingResult.hasErrors())
        {
            return "specificBook/add";
        }
        Book book = bookRepository.findById(id).get();
        Set<SpecificBook> books = book.getSpecificBooks();

        books.add(specificBook);
        book.setSpecificBooks(books);

        bookRepository.save(book);


        return "redirect:book/" + id;
    }

    @PostMapping("/{id}/filter")
    public String filter(@RequestParam(defaultValue = "") String filter,@PathVariable("id") Long id, Model model){

        model.addAttribute("books", bookRepository.findById(id).get().getSpecificBooks().stream()
            .filter(b->b.getUniqueCode().startsWith(filter))
            .collect(Collectors.toSet()));
        model.addAttribute("id", id);

        return "specificBook/table";
    }
}