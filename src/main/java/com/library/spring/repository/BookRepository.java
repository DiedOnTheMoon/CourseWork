package com.library.spring.repository;

import com.library.spring.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByBookNameStartingWith(String bookName);
    Book findBookByAuthorAuthorNameAndLanguageLanguageNameAndPublisherPublisherNameAndPublisherCityCityNameAndGenreGenreName(
            String authorName, String languageName, String publisherName, String cityName, String genreName );
}
