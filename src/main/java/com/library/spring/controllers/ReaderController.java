package com.library.spring.controllers;

import com.library.spring.models.Blacklist;
import com.library.spring.models.Reader;
import com.library.spring.models.SpecificBook;
import com.library.spring.models.SpecificBookReader;
import com.library.spring.repository.ReaderRepository;
import com.library.spring.repository.SpecificBookRepository;
import com.library.spring.service.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final SpecificBookRepository specificBookRepository;
    private final ReaderRepository readerRepository;

    public ReaderController(ReaderRepository readerRepository, SpecificBookRepository specificBookRepository) {
        this.readerRepository = readerRepository;
        this.specificBookRepository = specificBookRepository;
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

        reader.setBehaviorRank(0);
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
        BlacklistService.updateBlackList(readerRepository);

        model.addAttribute("reader", reader);
        model.addAttribute("books", reader.getSpecificBooksReader().stream()
                .filter(b -> !b.getReturn()).map(SpecificBookReader::getSpecificBook)
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
            books = reader.getSpecificBooksReader().stream()
                    .filter(SpecificBookReader::getReturn).map(SpecificBookReader::getSpecificBook)
                    .filter(b -> b.getBook().getBookName().startsWith(bookFilter))
                    .collect(Collectors.toSet());
        }else {
            books = reader.getSpecificBooksReader().stream()
                    .filter(SpecificBookReader::getReturn).map(SpecificBookReader::getSpecificBook)
                    .collect(Collectors.toSet());
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
            books = reader.getSpecificBooksReader().stream()
                    .filter(SpecificBookReader::getReturn)
                    .map(SpecificBookReader::getSpecificBook)
                    .filter(b -> b.getBook().getBookName().startsWith(bookFilter))
                    .collect(Collectors.toSet());
            this.bookFilter = bookFilter;
        }else {
            books = reader.getSpecificBooksReader().stream()
                    .filter(SpecificBookReader::getReturn)
                    .map(SpecificBookReader::getSpecificBook)
                    .collect(Collectors.toSet());
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

    @GetMapping("/take/{id}")
    public String take(
            @ModelAttribute("id") @PathVariable("id") Long id,
            Model model){
        model.addAttribute("readers", readerRepository.findAll());
        model.addAttribute("specificBookReader", new SpecificBookReader());
        return "reader/take";
    }

    @PostMapping("/take/{id}/{readerId}")
    public String postTake(
            @ModelAttribute("id") @PathVariable("id") Long id,
            @PathVariable("readerId") Long readerId,
            @ModelAttribute("specificBookReader") @Valid SpecificBookReader specificBookReader,
            BindingResult sbrBinding
    ){
        if(sbrBinding.hasErrors()){
            return "redirect:/reader/take/" + id + "/";
        }

        SpecificBook specificBookFromDB = specificBookRepository.findById(id).get();
        Reader reader = readerRepository.findById(readerId).get();
        specificBookFromDB.getSpecificBookReaders().add(specificBookReader);
        specificBookFromDB.setInPlace(false);
        specificBookReader.setReturn(false);
        specificBookReader.setReader(reader);
        specificBookReader.setSpecificBook(specificBookFromDB);
        reader.getSpecificBooksReader().add(specificBookReader);
        readerRepository.save(reader);

        return "redirect:/book/table/";
    }
}
