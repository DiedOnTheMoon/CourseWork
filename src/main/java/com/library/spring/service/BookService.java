package com.library.spring.service;

import com.library.spring.models.*;
import com.library.spring.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookService {
    private static  BlacklistRepository blacklistRepository;
    private static  ReaderRepository readerRepository;
    private static  BookRepository bookRepository;
    private static  SpecificBookReaderRepository specificBookReaderRepository;
    private static  SpecificBookRepository specificBookRepository;
    private static  CityRepository cityRepository;
    private static  AuthorRepository authorRepository;
    private static  GenreRepository genreRepository;
    private static  PublisherRepository publisherRepository;
    private static  LanguageRepository languageRepository;

    public BookService(BookRepository bookRepository, SpecificBookRepository specificBookRepository,
                       CityRepository cityRepository, AuthorRepository authorRepository, GenreRepository genreRepository,
                       PublisherRepository publisherRepository, LanguageRepository languageRepository,
                       ReaderRepository readerRepository, BlacklistRepository blacklistRepository,
                       SpecificBookReaderRepository specificBookReaderRepository) {
        BookService.bookRepository = bookRepository;
        BookService.specificBookRepository = specificBookRepository;
        BookService.cityRepository = cityRepository;
        BookService.authorRepository = authorRepository;
        BookService.genreRepository = genreRepository;
        BookService.publisherRepository = publisherRepository;
        BookService.languageRepository = languageRepository;
        BookService.readerRepository = readerRepository;
        BookService.blacklistRepository = blacklistRepository;
        BookService.specificBookReaderRepository = specificBookReaderRepository;
    }

    public static void addNewBook(SpecificBook specificBook){
        Genre genre = findGenre(specificBook.getBook().getGenre());
        City city = findCity(specificBook.getBook().getPublisher().getCity());
        Author author = findAuthor(specificBook.getBook().getAuthor());
        Publisher publisher = findPublisher(specificBook.getBook().getPublisher());
        Language language = findLanguage(specificBook.getBook().getLanguage());

        specificBook.getBook().setGenre(genre);
        specificBook.getBook().setPublisher(publisher);
        specificBook.getBook().getPublisher().setCity(city);
        specificBook.getBook().setAuthor(author);
        specificBook.getBook().setLanguage(language);
        Book bookExample = specificBook.getBook();

        Book book = bookRepository.findBook(bookExample.getBookName(), bookExample.getPrice(), bookExample.getYear(),
                language.getLanguageName(), publisher.getPublisherName(), city.getCityName(), genre.getGenreName());
        if(book == null){
            specificBookRepository.save(specificBook);
        }else{
            specificBook.setBook(book);
            book.getSpecificBooks().add(specificBook);
            bookRepository.save(book);
        }
    }

    public static void updateBook(Book book){
        Genre genre = findGenre(book.getGenre());
        City city = findCity(book.getPublisher().getCity());
        Author author = findAuthor(book.getAuthor());
        Publisher publisher = findPublisher(book.getPublisher());
        Language language = findLanguage(book.getLanguage());

        book.setGenre(genre);
        book.setPublisher(publisher);
        book.setAuthor(author);
        book.setLanguage(language);
        book.getPublisher().setCity(city);

        bookRepository.save(book);
    }

    public static void returnBook(Long id, Long readerId){
        Reader reader = readerRepository.findById(readerId).get();
        SpecificBook book = specificBookRepository.findById(id).get();

        SpecificBookReader spr = book.getSpecificBookReaders().stream()
                .filter(s -> !s.getReturn()).findFirst().get();

        spr.setReturn(true);
        spr.setRealReturnDate(LocalDate.now());
        book.setInPlace(true);

        SpecificBookReader specificBookReader = book.getSpecificBookReaders().stream()
                .filter( b -> !b.getReturn()).findFirst().get();
        specificBookReader.setReturn(true);
        specificBookReader.setRealReturnDate(LocalDate.now());

        Blacklist bl = book.getBlacklists().stream()
                .filter( b -> !b.getPaid()).findFirst().orElse(null);

        if(bl != null) {
            reader.setBehaviorRank(reader.getBehaviorRank() - 1);
            bl.setPaid(true);
            blacklistRepository.save(bl);
        }else{
            reader.setBehaviorRank(reader.getBehaviorRank() + 1);
        }

        specificBookReaderRepository.save(specificBookReader);
        readerRepository.save(reader);
    }

    private static Genre findGenre(Genre genre){
        Genre genreFromDb = genreRepository.findByGenreName(genre.getGenreName());
        return genreFromDb == null ? genre : genreFromDb;
    }

    private static City findCity(City city){
        City cityFromDb = cityRepository.findCityByCityName(city.getCityName());
        return cityFromDb == null ? city : cityFromDb;
    }

    private static Author findAuthor(Author author){
        Author authFromDb = authorRepository.findAuthorByAuthorName(author.getAuthorName());
        return  authFromDb == null ? author : authFromDb;
    }

    private static Publisher findPublisher(Publisher publisher){
        Publisher publisherFromDb = publisherRepository.findPublisherByPublisherName(publisher.getPublisherName());
        return publisherFromDb == null ? publisher : publisherFromDb;
    }

    private static Language findLanguage(Language language){
        Language languageFromDb = languageRepository.findLanguageByLanguageName(language.getLanguageName());
        return  languageFromDb == null ? language : languageFromDb;
    }
}
