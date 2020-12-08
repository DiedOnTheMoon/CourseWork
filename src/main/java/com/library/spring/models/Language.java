package com.library.spring.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "LANGUAGE")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    @NotNull(message = "name should not be blank")
    @NotBlank(message = "language cannot be blank")
    @Pattern(regexp = "^[A-Z][a-z]{3,254}", message = "Name is not correct. Example: Lang")
    @Length(min=4, max=255, message = "language length is not correct(min=4, max=255)")
    private String languageName;
    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
