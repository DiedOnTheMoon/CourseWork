package com.library.spring.models;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "READER")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;
    @Column(name = "FIRST_NAME")
    @NotBlank(message = "First name cannot be blank")
    @Length(min=3, max=255, message = "First name length isn't correct (min=3, max=255)")
    private String firstName;
    @Column(name = "LAST_NAME")
    @NotBlank(message = "Last name cannot be blank")
    @Length(min=3, max=255, message = "Last name length isn't correct (min=3, max=255)")
    private String lastName;
    @Column(name = "DATE_OF_BIRTH")
    @NotBlank(message = " cannot be blank")
    @Length(min=3, max=255, message = "publisher length isn't correct (min=3, max=255)")
    private Date dateOfBirth;
    @Column(name = "ADDRESS")
    @NotBlank(message = "publisher cannot be blank")
    @Length(min=3, max=255, message = "publisher length isn't correct (min=3, max=255)")
    private String address;
    @Column(name = "PHONE")
    @NotBlank(message = "publisher cannot be blank")
    @Length(min=3, max=255, message = "publisher length isn't correct (min=3, max=255)")
    private String phone;
    @Column(name = "BEHAVIOR_RANK")
    @NotBlank(message = "publisher cannot be blank")
    @Length(min=3, max=255, message = "publisher length isn't correct (min=3, max=255)")
    private int behaviorRank;
    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private Set<Blacklist> blacklists;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "SPECIFIC_BOOK_READER",
            joinColumns = @JoinColumn(name="READER_ID"),
            inverseJoinColumns = @JoinColumn(name = "SPECIFIC_BOOK_ID")
    )
    private Set<SpecificBook> specificBooks;

    public Reader(Long id, String firstName, String lastName, Date dateOfBirth, String address, String phone,
                  int behaviorRank, Set<Blacklist> blacklists) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.behaviorRank = behaviorRank;
        this.blacklists = blacklists;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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

    public Set<SpecificBook> getSpecificBooks() {
        return specificBooks;
    }

    public void setSpecificBooks(Set<SpecificBook> specificBooks) {
        this.specificBooks = specificBooks;
    }
}
