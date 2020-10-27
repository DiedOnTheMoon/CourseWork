package com.library.spring.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "BOOK")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String bookName;
    @Column(name = "YEAR")
    private Integer year;
    @Column(name = "PRICE")

    private Integer price;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "LANGUAGE_ID", nullable = false)
    private Language language;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "PUBLISHER_ID", nullable = false)
    private Publisher   publisher;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "AUTHOR_BOOK",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID")
    )
    private Set<Author> authors;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "GENRE_BOOK",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID")
    )
    private Set<Genre> genres;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<SpecificBook> specificBooks;

    public Book() {
    }

    public Book(Long id, String bookName, Integer year, Integer price, Language language, Publisher publisher,
                Set<Author> authors, Set<Genre> genres, Set<SpecificBook> specificBooks) {
        this.id = id;
        this.bookName = bookName;
        this.year = year;
        this.price = price;
        this.language = language;
        this.publisher = publisher;
        this.authors = authors;
        this.genres = genres;
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

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<SpecificBook> getSpecificBooks() {
        return specificBooks;
    }

    public void setSpecificBooks(Set<SpecificBook> specificBooks) {
        this.specificBooks = specificBooks;
    }

    public String getAuthorsInString(){
        StringBuilder str = new StringBuilder();

        for(Author a : authors){

            str.append(a.getAuthorName()).append(",");
        }

        if(str.charAt(str.length() - 1) == ','){
            str.deleteCharAt(str.length() - 1);
        }

        return str.toString();
    }
}