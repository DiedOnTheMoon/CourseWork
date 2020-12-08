package com.library.spring.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

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
    @Column(name = "SHELF")
    @NotNull(message = "shelf should not be null")
    @NotBlank(message = "shelf can't be blank")
    @Length(min=3, max=255, message = "shelf length should be >=3 and <= 255")
    private String shelf;
    @Column(name = "RANK")
    @NotNull(message = "rank should not be null")
    @NotBlank(message = "rank can't be blank")
    @Length(min=3, max=255, message = "rank length should be >=3 and <= 255")
    private String rank;
    @Column(name = "ROOM")
    @NotNull(message = "room should not be null")
    @NotBlank(message = "room can't be blank")
    @Length(min=3, max=255, message = "room length should be >= 3 and <= 255 ")
    private String room;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID", nullable = false)
    private Book book;
    @OneToMany(mappedBy = "specificBook", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SpecificBookReader> specificBookReaders;
    @OneToMany(mappedBy = "specificBook", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Blacklist> blacklists;

    public SpecificBook() {

    }

    public SpecificBook(Long id, String uniqueCode, Boolean inPlace,
                        String shelf, String rank, String room, Book book,Set<SpecificBookReader> specificBookReaders,
                        Set<Blacklist> blacklist) {
        this.id = id;
        this.uniqueCode = uniqueCode;
        this.inPlace = inPlace;
        this.shelf = shelf;
        this.rank = rank;
        this.room = room;
        this.book = book;
        this.specificBookReaders = specificBookReaders;
        this.blacklists = blacklist;
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

    public Set<Blacklist> getBlacklists() {
        return blacklists;
    }

    public void setBlacklists(Set<Blacklist> blacklists) {
        this.blacklists = blacklists;
    }

    public int getSizeSpecificBooksReader(){
        return specificBookReaders.size();
    }

    public LocalDate getReturnDate(){
        return specificBookReaders.stream().filter( b -> !b.getReturn()).findFirst().get().getReturnDate();
    }
}