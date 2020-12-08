package com.library.spring.models;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "READER")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    @NotNull(message = "First Name should not be null")
    @NotBlank(message = "First name cannot be blank")
    @Length(min=3, max=255, message = "First name length isn't correct (min=3, max=255)")
    private String firstName;
    @Column(name = "LAST_NAME")
    @NotNull(message = "Last Name should not be null")
    @NotBlank(message = "Last name cannot be blank")
    @Length(min=3, max=255, message = "Last name length isn't correct (min=3, max=255)")
    private String lastName;
    @Column(name = "DATE_OF_BIRTH")
    @Past(message = "date of Birth should be in past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    @Column(name = "ADDRESS")
    @NotBlank(message = "address cannot be blank")
    @Length(min=3, max=255, message = "address length isn't correct (min=3, max=255)")
    private String address;
    @Column(name = "PHONE")
    @NotBlank(message = "phone cannot be blank")
    @Length(min=3, max=255, message = "phone length isn't correct (min=3, max=255)")
    private String phone;
    @Column(name = "BEHAVIOR_RANK")
    private int behaviorRank;
    @OneToMany(mappedBy = "reader", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Blacklist> blacklists;
    @OneToMany(mappedBy = "reader", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SpecificBookReader> specificBooksReader;

    public Reader(Long id, String firstName, String lastName, LocalDate dateOfBirth, String address,
                  String phone, int behaviorRank, Set<Blacklist> blacklists, Set<SpecificBookReader> specificBooksReader) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.behaviorRank = behaviorRank;
        this.blacklists = blacklists;
        this.specificBooksReader = specificBooksReader;
    }

    public Reader() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getBehaviorRank() {
        return behaviorRank;
    }

    public void setBehaviorRank(int behaviorRank) {
        this.behaviorRank = behaviorRank;
    }

    public Set<Blacklist> getBlacklists() {
        return blacklists;
    }

    public void setBlacklists(Set<Blacklist> blacklists) {
        this.blacklists = blacklists;
    }

    public Set<SpecificBookReader> getSpecificBooksReader() {
        return specificBooksReader;
    }

    public void setSpecificBooksReader(Set<SpecificBookReader> specificBooksReader) {
        this.specificBooksReader = specificBooksReader;
    }

    public int getSizeBlacklist(){
        return blacklists.stream().filter( b -> !b.getPaid()).collect(Collectors.toSet()).size();
    }

    public int getSizeBooks(){
        return specificBooksReader.stream().filter(b -> !b.getReturn()).collect(Collectors.toSet()).size();
    }
}
