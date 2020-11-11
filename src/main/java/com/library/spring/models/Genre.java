package com.library.spring.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "GENRE")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String genreName;
    @ManyToMany(mappedBy = "genres", cascade = CascadeType.ALL)
    private Set<Book> books = new HashSet<>();

    public Genre() {
    }

    public Genre(Long id, String genreName, Set<Book> books) {
        this.id = id;
        this.genreName = genreName;
        this.books = books;
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
