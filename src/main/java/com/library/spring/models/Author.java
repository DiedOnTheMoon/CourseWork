package com.library.spring.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "AUTHOR")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String authorName;
    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
    private Set<Book> books;

    public Author() {
    }

    public Author(String authorName){
        this.authorName = authorName;
    }

    public Author(Long id, String authorName, Set<Book> books) {
        this.id = id;
        this.authorName = authorName;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
