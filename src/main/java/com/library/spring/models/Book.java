package com.library.spring.models;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

@Entity
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    @NotBlank(message = "please fill Book name!")
    @Length(min=1, max=255, message = "name is not correct(min=1, max=255)")
    private String bookName;
    @Column(name = "YEAR")
    @Range(min=1900, max=2020, message = "year is not correct (min = 1900, max = 2020)")
    private Integer year;
    @Column(name = "PRICE")
    @Range(min=1000, max=10000, message="price is not correct (min=10.00  max = 100.00)")
    private Integer price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "LANGUAGE_ID", nullable = false)
    private Language language;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PUBLISHER_ID", nullable = false)
    private Publisher   publisher;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "AUTHOR_ID", nullable = false)
    private Author author;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="GENRE_ID", nullable = false)
    private Genre genre;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<SpecificBook> specificBooks;

    public Book() {
    }

    public Book(Long id, String bookName, Integer year, Integer price, Language language, Publisher publisher,
                Author author, Genre genre, Set<SpecificBook> specificBooks) {
        this.id = id;
        this.bookName = bookName;
        this.year = year;
        this.price = price;
        this.language = language;
        this.publisher = publisher;
        this.author = author;
        this.genre = genre;
        this.specificBooks = specificBooks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Language getLanguage() {
        return language;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<SpecificBook> getSpecificBooks() {
        return specificBooks;
    }

    public void setSpecificBooks(Set<SpecificBook> specificBooks) {
        this.specificBooks = specificBooks;
    }

}