package com.library.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "BLACKLIST")
public class Blacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "PRICE_BY_DAY")
    private int priceByDay;
    @Column(name = "COUNT_DAYS")
    private int countDays;
    @Column(name = "SPECIFIC_BOOK_ID")
    private int specificBookId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "READER_ID")
    private Reader reader;

    public Blacklist() {
    }

    public Blacklist(Long id, int priceByDay, int countDays, int specificBookId, Reader reader) {
        this.id = id;
        this.priceByDay = priceByDay;
        this.countDays = countDays;
        this.specificBookId = specificBookId;
        this.reader = reader;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPriceByDay() {
        return priceByDay;
    }

    public void setPriceByDay(int priceByDay) {
        this.priceByDay = priceByDay;
    }

    public int getCountDays() {
        return countDays;
    }

    public void setCountDays(int countDays) {
        this.countDays = countDays;
    }

    public int getSpecificBookId() {
        return specificBookId;
    }

    public void setSpecificBookId(int specificBookId) {
        this.specificBookId = specificBookId;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}