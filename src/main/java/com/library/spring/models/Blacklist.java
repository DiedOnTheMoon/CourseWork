package com.library.spring.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "BLACKLIST")
public class Blacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @NotNull
    @Column(name = "PRICE")
    private Long price;
    @NotNull
    @Column(name = "ISPAID")
    private Boolean isPaid;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "READER_ID", nullable = false)
    private Reader reader;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SPECIFIC_BOOK_ID", referencedColumnName = "ID", nullable = false)
    private SpecificBook specificBook;

    public Blacklist() {
    }

    public Blacklist(Long id, Long price, Boolean isPaid, Reader reader, SpecificBook specificBook) {
        this.id = id;
        this.price = price;
        this.isPaid = isPaid;
        this.reader = reader;
        this.specificBook = specificBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public SpecificBook getSpecificBook() {
        return specificBook;
    }

    public void setSpecificBook(SpecificBook specificBook) {
        this.specificBook = specificBook;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public String normalRepresentation(){
        return String.format("%d.%2d", price / 100, price % 100);
    }
}