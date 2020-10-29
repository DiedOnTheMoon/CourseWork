package com.library.spring.controllers;

import com.library.spring.models.*;
import com.library.spring.repository.BookRepository;
import com.library.spring.repository.SpecificBookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController {
    private final SpecificBookRepository specificBookRepository;
    private final BookRepository bookRepository;

    public BookController(SpecificBookRepository specificBookRepository, BookRepository bookRepository) {
        this.specificBookRepository = specificBookRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/add")
    public String  getAddBook(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("author", new Author());
        model.addAttribute("city", new City());
        model.addAttribute("publisher", new Publisher());
        model.addAttribute("specificBook", new SpecificBook());
        model.addAttribute("language", new Language());
        model.addAttribute("genre", new Genre());

        return "book/add";
    }

    @PostMapping("/add")
    public String addBook(
            Book book,
            Author author,
            City city,
            Publisher publisher,
            SpecificBook specificBook,
            Language language,
            Genre genre
    ){
        HashSet<Author> authors = new HashSet<>();
        HashSet<Genre> genres = new HashSet<>();

        authors.add(author);
        genres.add(genre);
        publisher.setCity(city);
        book.setPublisher(publisher);
        book.setAuthors(authors);
        book.setGenres(genres);
        book.setLanguage(language);
        specificBook.setBook(book);
        specificBook.setInPlace(true);
        specificBook.setUniqueCode(UUID.randomUUID().toString() + "/" + book.getBookName() +
                "/" + book.getLanguage().getLanguageName() + "/" + book.getPublisher().getPublisherName() +
                "/" + book.getYear() + "/" + book.getPrice());

        specificBookRepository.save(specificBook);

        System.out.println(specificBook);

        return "/main/main";
    }

    @GetMapping("/{id}")
    public String getTable(@PathVariable String id,  Model model){

        model.addAttribute("books", bookRepository.findById(Long.parseLong(id)).get().getSpecificBooks());

        return "specificBook/table";
    }
}