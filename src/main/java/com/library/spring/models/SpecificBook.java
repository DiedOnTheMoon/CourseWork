package com.library.spring.models;

import javax.persistence.*;
import java.util.Set;
import java.time.LocalDate;

@Entity
@Table(name = "SPECIFIC_BOOK")
public class SpecificBook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "UNIQUE_CODE")
    private String uniqueCode;
    @Column(name = "IN_PLACE")
    private Boolean inPlace;
    @Column(name = "DATE_OF_ISSUE")
    private LocalDate dateOfIssue;
    @Column(name = "RETURN_DATE")
    private LocalDate returnDate;
    @Column(name = "SHELF")
    private String shelf;
    @Column(name = "RANK")
    private String rank;
    @Column(name = "ROOM")
    private String room;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;
    @ManyToMany(mappedBy = "specificBooks", cascade = CascadeType.ALL)
    private Set<Reader> readers;
    @OneToOne(mappedBy = "specificBook")
    private Blacklist blacklist;

    public SpecificBook() {

    }

    public SpecificBook(Long id, String uniqueCode, Boolean inPlace, LocalDate dateOfIssue, LocalDate returnDate,
                        String shelf, String rank, String room, Book book) {
        this.id = id;
        this.uniqueCode = uniqueCode;
        this.inPlace = inPlace;
        this.dateOfIssue = dateOfIssue;
        this.returnDate = returnDate;
        this.shelf = shelf;
        this.rank = rank;
        this.room = room;
        this.book = book;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueCode() {
        return uniqueCode;
    }

    public void setUniqueCode(String uniqueCode) {
        this.uniqueCode = uniqueCode;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Boolean getInPlace() {
        return inPlace;
    }

    public void setInPlace(Boolean inPlace) {
        this.inPlace = inPlace;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Set<Reader> getReaders() {
        return readers;
    }

    public void setReaders(Set<Reader> readers) {
        this.readers = readers;
    }

    public Blacklist getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Blacklist blacklist) {
        this.blacklist = blacklist;
    }
}