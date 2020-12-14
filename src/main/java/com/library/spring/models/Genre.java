package com.library.spring.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "GENRE")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    @NotNull(message = "name should not be null")
    @Pattern(regexp = "^[A-Z][a-z]{3,254}", message = "Name is not correct. Example: Genre")
    @NotBlank(message = "genre cannot be blank")
    @Length(min=4, max=255, message = "genres length is not correct (min=4, max=255)")
    private String genreName;
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> books;

    public Genre() {
    }

    public Genre(Long id, String genreName, Set<Book> books) {
        this.id = id;
        this.genreName = genreName;
        this.books = books;
    }

    public Genre(Genre genre){
        this(genre.id, genre.genreName, genre.books);
    }

    public Genre(String genreName){
        this.genreName = genreName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
