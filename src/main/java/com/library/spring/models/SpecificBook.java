package com.library.spring.models;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.util.Set;
import java.time.LocalDate;

@Entity
@Table(name = "SPECIFIC_BOOK", uniqueConstraints = @UniqueConstraint(columnNames = {"SHELF", "RANK", "ROOM"}))
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
    @PastOrPresent(message = "date Of Issue must be present")
    @FutureOrPresent(message = "date of Issue must be present")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfIssue;
    @Column(name = "RETURN_DATE")
    @Future(message = "return Date must be in future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;
    @Column(name = "SHELF")
    @NotBlank(message = "shelf can't be blank")
    @Length(min=3, max=255, message = "shelf length should be >=3 and <= 255")
    private String shelf;
    @Column(name = "RANK")
    @NotBlank(message = "rank can't be blank")
    @Length(min=3, max=255, message = "rank length should be >=3 and <= 255")
    private String rank;
    @Column(name = "ROOM")
    @NotBlank(message = "room can't be blank")
    @Length(min=3, max=255, message = "room length should be >= 3 and <= 255 ")
    private String room;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;
    @OneToMany(mappedBy = "specificBook", cascade = CascadeType.ALL)
    private Set<SpecificBookReader> specificBookReaders;
    @OneToOne(mappedBy = "specificBook", orphanRemoval = true)
    private Blacklist blacklist;

    public SpecificBook() {

    }

    public SpecificBook(Long id, String uniqueCode, Boolean inPlace, LocalDate dateOfIssue, LocalDate returnDate,
                        String shelf, String rank, String room, Book book,Set<SpecificBookReader> specificBookReaders,
                        Blacklist blacklist) {
        this.id = id;
        this.uniqueCode = uniqueCode;
        this.inPlace = inPlace;
        this.dateOfIssue = dateOfIssue;
        this.returnDate = returnDate;
        this.shelf = shelf;
        this.rank = rank;
        this.room = room;
        this.book = book;
        this.specificBookReaders = specificBookReaders;
        this.blacklist = blacklist;
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

    public Set<SpecificBookReader> getSpecificBookReaders() {
        return specificBookReaders;
    }

    public void setSpecificBookReaders(Set<SpecificBookReader> specificBookReaders) {
        this.specificBookReaders = specificBookReaders;
    }

    public Blacklist getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(Blacklist blacklist) {
        this.blacklist = blacklist;
    }
}