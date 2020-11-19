package com.library.spring.controllers;

import com.library.spring.models.*;
import com.library.spring.repository.BlacklistRepository;
import com.library.spring.repository.BookRepository;
import com.library.spring.repository.SpecificBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
public class BookController {
    private final SpecificBookRepository specificBookRepository;
    private final BookRepository bookRepository;
    private final BlacklistRepository blacklistRepository;

    public BookController(SpecificBookRepository specificBookRepository, BookRepository bookRepository,
                          BlacklistRepository blacklistRepository) {
        this.specificBookRepository = specificBookRepository;
        this.bookRepository = bookRepository;
        this.blacklistRepository = blacklistRepository;
    }

    @GetMapping("/add")
    public String  getAddBook(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("city", new City());
        model.addAttribute("publisher", new Publisher());
        model.addAttribute("specificBook", new SpecificBook());
        model.addAttribute("language", new Language());
        model.addAttribute("authors", "");
        model.addAttribute("generes", "");

        return "book/add";
    }

    @PostMapping("/add")
    public String addBook(
            @Valid Book book,
            @Valid String authors,
            @Valid City city,
            @Valid Publisher publisher,
            @Valid SpecificBook specificBook,
            @Valid Language language,
            String generes,
            BindingResult bindingResult,
            Model model
    ){
        String ret = "redirect:/book/table";
        var map = ControllersUtil.isCorrect(authors, generes);
        if(bindingResult.hasErrors() || !map.isEmpty()){
            Map<String, String> errors = ControllersUtil.getErrors(bindingResult);

            model.mergeAttributes(errors);
            model.mergeAttributes(map);
            model.addAttribute("book", book);
            model.addAttribute("city", city);
            model.addAttribute("publisher", publisher);
            model.addAttribute("specificBook", specificBook);
            model.addAttribute("language", language);
            model.addAttribute("authors", authors);
            model.addAttribute("generes", generes);

            ret = "book/add";
        }else {
            Set<Genre> setGeneres = new HashSet<>();
            Set<Author> setAuthors = new HashSet<>();

            String[] generesArr = generes.split(",");
            String[] authorsArr = authors.split(",");

            for (String item : generesArr) {
                setGeneres.add(new Genre(item));
            }

            for (String item : authorsArr) {
                setAuthors.add(new Author(item));
            }

            publisher.setCity(city);
            book.setPublisher(publisher);
            book.setAuthors(setAuthors);
            book.setGenres(setGeneres);
            book.setLanguage(language);
            specificBook.setBook(book);
            specificBook.setInPlace(true);
            specificBook.setUniqueCode(UUID.randomUUID().toString() + "/" + book.getBookName() +
                    "/" + book.getLanguage().getLanguageName() + "/" + book.getPublisher().getPublisherName() +
                    "/" + book.getYear() + "/" + book.getPrice());

            specificBookRepository.save(specificBook);

            System.out.println(specificBook);
        }
        return ret;
    }

    @GetMapping("/{id}")
    public String getSpecBookTable(@PathVariable Long id,  Model model){

        model.addAttribute("books", bookRepository.findById(id).get().getSpecificBooks());
        model.addAttribute("id", id);

        return "specificBook/table";
    }

    @PostMapping("/return/{id}/{readerId}")
    public String returnBook(@PathVariable Long id, @PathVariable String readerId){

        SpecificBook book = specificBookRepository.findById(id).get();

        book.setReturnDate(null);
        book.setDateOfIssue(null);
        book.setInPlace(true);

        if(book.getBlacklist() != null) {
            blacklistRepository.delete(book.getBlacklist());
        }

        return "redirect:/reader/" + readerId;
    }

    @PostMapping("/filter")
    public String filter(@RequestParam(defaultValue = "") String filter, Model model){
        model.addAttribute("books", bookRepository.findAll().stream()
                .filter(b -> b.getBookName().startsWith(filter)).collect(Collectors.toSet()));

        return "book/table";
    }

    @GetMapping("/table")
    public String getBooksTable(Model model){
        model.addAttribute("books", bookRepository.findAll());
        return "book/table";
    }
}