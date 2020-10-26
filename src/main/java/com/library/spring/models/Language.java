package com.library.spring.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "LANGUAGE")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String languageName;
    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL)
    private Set<Book> books;

    public Language() {
    }

    public Language(Long id, String languageName, Set<Book> books) {
        this.id = id;
        this.languageName = languageName;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
