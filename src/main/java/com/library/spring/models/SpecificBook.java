package com.library.spring.models;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "SPECIFIC_BOOK")
public class SpecificBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "UNIQUE_CODE")
    private String uniqueCode;
    @Column(name = "IN_PLACE")
    private Boolean inPlace;
    @Column(name = "DATE_OF_ISSUE")
    private DateTime dateOfIssue;
    @Column(name = "RETURN_DATE")
    private DateTime returnDate;
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

    public SpecificBook(Long id, String uniqueCode, Boolean inPlace, DateTime dateOfIssue, DateTime returnDate,
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

    public DateTime getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(DateTime dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public DateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(DateTime returnDate) {
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