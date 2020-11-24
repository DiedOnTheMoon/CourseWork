package com.library.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "BLACKLIST")
public class Blacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "ALL_PRICE")
    private Long allPrice;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "READER_ID")
    private Reader reader;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SPECIFIC_BOOK_ID", referencedColumnName = "ID")
    private SpecificBook specificBook;

    public Blacklist() {
    }

    public Blacklist(Long id, Long allPrice, Reader reader, SpecificBook specificBook) {
        this.id = id;
        this.allPrice = allPrice;
        this.reader = reader;
        this.specificBook = specificBook;
    }

    public Blacklist(Long allPrice, Reader reader, SpecificBook specificBook){
        this.allPrice = allPrice;
        this.reader = reader;
        this.specificBook = specificBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAllPrice() {
        return allPrice;
    }

    public void setAllPrice(Long allPrice) {
        this.allPrice = allPrice;
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

    public String normalRepresentation(){
        return String.format("%d.%2d", allPrice / 100, allPrice % 100);
    }
}