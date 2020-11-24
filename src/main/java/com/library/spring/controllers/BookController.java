package com.library.spring.controllers;

import com.library.spring.models.*;
import com.library.spring.repository.*;
import com.library.spring.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
public class BookController {
    private final SpecificBookReaderRepository specificBookReaderRepository;
    private final ReaderRepository readerRepository;
    private final SpecificBookRepository specificBookRepository;
    private final BookRepository bookRepository;
    private final BlacklistRepository blacklistRepository;

    public BookController(SpecificBookRepository specificBookRepository, BookRepository bookRepository,
                          BlacklistRepository blacklistRepository, ReaderRepository readerRepository, SpecificBookReaderRepository specificBookReaderRepository) {
        this.specificBookRepository = specificBookRepository;
        this.bookRepository = bookRepository;
        this.blacklistRepository = blacklistRepository;
        this.readerRepository = readerRepository;
        this.specificBookReaderRepository = specificBookReaderRepository;
    }

    @GetMapping("/add")
    public String  getAddBook(
            @ModelAttribute("book") Book book,
            @ModelAttribute("city") City city,
            @ModelAttribute("publisher") Publisher publisher,
            @ModelAttribute("specificBook") SpecificBook specificBook,
            @ModelAttribute("language") Language language,
            @ModelAttribute("author") Author author,
            @ModelAttribute("genre") Genre genre
    ){

        return "book/add";
    }

    @PostMapping("/add")
    public String addBook(
            @ModelAttribute("book") @Valid Book book,
            BindingResult bookBinding,
            @ModelAttribute("city") @Valid City city,
            BindingResult cityBinding,
            @ModelAttribute("publisher") @Valid Publisher publisher,
            BindingResult publisherBinding,
            @ModelAttribute("specificBook") @Valid SpecificBook specificBook,
            BindingResult specificBookBinding,
            @ModelAttribute("language") @Valid Language language,
            BindingResult languageBinding,
            @ModelAttribute("genre") @Valid Genre genre,
            BindingResult genreBinding,
            @ModelAttribute("author") @Valid Author author,
            BindingResult authorBinding
    ){
        if(bookBinding.hasErrors() || cityBinding.hasErrors() || publisherBinding.hasErrors()
            || specificBookBinding.hasErrors() || languageBinding.hasErrors() || genreBinding.hasErrors()
            || authorBinding.hasErrors()){


            return "book/add";
        }else {

            publisher.setCity(city);
            book.setPublisher(publisher);
            book.setAuthor(author);
            book.setGenre(genre);
            book.setLanguage(language);
            specificBook.setBook(book);
            specificBook.setInPlace(true);
            specificBook.setUniqueCode(UUID.randomUUID().toString() + "/" + book.getBookName() +
                    "/" + book.getLanguage().getLanguageName() + "/" + book.getPublisher().getPublisherName() +
                    "/" + book.getYear() + "/" + book.getPrice());

            BookService.addNewBook(specificBook);
        }
        return "redirect:/book/table/";
    }

    @GetMapping("/{id}")
    public String getSpecBookTable(@PathVariable("id") String id, Model model){

        model.addAttribute("books", bookRepository.findById(Long.parseLong(id)).get().getSpecificBooks());
        model.addAttribute("id", id);

        return "specificBook/table";
    }

    @PostMapping("/return/{id}/{readerId}")
    public String returnBook(@PathVariable("id") Long id, @PathVariable("readerId") Long readerId){

        Reader reader = readerRepository.findById(readerId).get();
        SpecificBook book = specificBookRepository.findById(id).get();

        book.setReturnDate(null);
        book.setDateOfIssue(null);
        book.setInPlace(true);

        SpecificBookReader specificBookReader = book.getSpecificBookReaders().stream()
                .filter( b -> !b.getReturn()).findFirst().get();
        specificBookReader.setReturn(true);

        if(book.getBlacklist() != null) {
            reader.setBehaviorRank(reader.getBehaviorRank() - 1);
            blacklistRepository.delete(book.getBlacklist());
        }else{
            reader.setBehaviorRank(reader.getBehaviorRank() + 1);
        }

        specificBookReaderRepository.save(specificBookReader);
        readerRepository.save(reader);

        return "redirect:/reader/" + readerId + "/";
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

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") Long id,
                           @ModelAttribute("genre") Genre genre,
                           @ModelAttribute("author") Author author,
                           @ModelAttribute("publisher") Publisher publisher,
                           @ModelAttribute("city") City city,
                           @ModelAttribute("language") Language language,
                           Model model){
        Book book = bookRepository.findById(id).get();
        model.addAttribute("book", book);
        genre.setGenreName(book.getGenre().getGenreName());
        author.setAuthorName(book.getAuthor().getAuthorName());
        language.setLanguageName(book.getLanguage().getLanguageName());
        city.setCityName(book.getPublisher().getCity().getCityName());
        publisher.setPublisherName(book.getPublisher().getPublisherName());
        publisher.setCity(city);

        return "book/edit";
    }

    @PatchMapping("/edit/{id}")
    public String saveBook(
            @ModelAttribute("id") @PathVariable("id") Long id,
            @ModelAttribute("book") @Valid Book book,
            BindingResult bindingResult,
            @ModelAttribute("genre") @Valid Genre genre,
            BindingResult genreBinding,
            @ModelAttribute("author") @Valid Author author,
            BindingResult authorBinding,
            @ModelAttribute("language") @Valid Language language,
            BindingResult languageBinding,
            @ModelAttribute("publisher") @Valid Publisher publisher,
            BindingResult publisherBinding,
            @ModelAttribute("city") @Valid City city,
            BindingResult cityBinding
    ){
        if(bindingResult.hasErrors() || genreBinding.hasErrors() || authorBinding.hasErrors() ||
                publisherBinding.hasErrors() || cityBinding.hasErrors() || languageBinding.hasErrors()){
            return "book/edit";
        }

        Book book1 = bookRepository.findById(id).get();

        book1.setBookName(book.getBookName());
        book1.setPrice(book.getPrice());
        book1.setYear(book.getYear());
        book1.setLanguage(language);
        book1.setGenre(genre);
        book1.setAuthor(author);
        book1.setPublisher(publisher);
        book1.getPublisher().setCity(city);

        BookService.updateBook(book1);

        return "redirect:/book/table/";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id){
        bookRepository.deleteById(id);
        return "redirect:/book/table/";
    }
}