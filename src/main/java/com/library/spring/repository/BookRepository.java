package com.library.spring.repository;

import com.library.spring.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByBookNameStartingWith(String bookName);
    @Query("select b from Book b where b.bookName =: bookName and b.price =: price and b.year =: year " +
            "and b.language.languageName =: langName and  b.publisher.publisherName =: publisherName " +
            "and b.publisher.city.cityName =: cityName and b.genre.genreName =: genreName")
    Book findBook(
            @Param("bookName") String bookName,
            @Param("price") Integer price,
            @Param("year") Integer year,
            @Param("langName") String languageName,
            @Param("pubName") String publisherName,
            @Param("cityName") String cityName,
            @Param("genreName") String genreName );
}
