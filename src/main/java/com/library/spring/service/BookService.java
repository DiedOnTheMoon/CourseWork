package com.library.spring.service;

import com.library.spring.models.*;
import com.library.spring.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    @Autowired
    private static BookRepository bookRepository;
    @Autowired
    private static SpecificBookRepository specificBookRepository;
    @Autowired
    private static CityRepository cityRepository;
    @Autowired
    private static AuthorRepository authorRepository;
    @Autowired
    private static GenreRepository genreRepository;
    @Autowired
    private static PublisherRepository publisherRepository;
    @Autowired
    private static LanguageRepository languageRepository;

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

        specificBookRepository.save(specificBook);
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

    private static Genre findGenre(Genre genre){
        Genre genreFromDb = genreRepository.findOneByGenreName(genre.getGenreName());
        return genreFromDb == null ? genre : genreFromDb;
    }

    private static City findCity(City city){
        City cityFromDb = cityRepository.findOneByCityName(city.getCityName());
        return cityFromDb == null ? city : cityFromDb;
    }

    private static Author findAuthor(Author author){
        Author authFromDb = authorRepository.findOneByAuthorName(author.getAuthorName());
        return  authFromDb == null ? author : authFromDb;
    }

    private static Publisher findPublisher(Publisher publisher){
        Publisher publisherFromDb = publisherRepository.findOneByPublisherName(publisher.getPublisherName());
        return publisherFromDb == null ? publisher : publisherFromDb;
    }

    private static Language findLanguage(Language language){
        Language languageFromDb = languageRepository.findOneByLanguageName(language.getLanguageName());
        return  languageFromDb == null ? language : languageFromDb;
    }
}
