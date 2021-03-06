package com.library.spring.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PUBLISHER")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NAME")
    private String publisherName;
    @ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CITY_ID")
    private City city;
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private Set<Book> books;

    public Publisher() {
    }

    public Publisher(Long id, String publisherName, City city, Set<Book> books) {
        this.id = id;
        this.publisherName = publisherName;
        this.city = city;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City cities) {
        this.city = cities;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
