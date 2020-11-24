package com.library.spring.models;

import javax.persistence.*;

@Entity
@Table(name = "SPECIFIC_BOOK_READER")
public class SpecificBookReader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Column(name="IS_RETURN")
    private Boolean isReturn;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "READER_ID", nullable = false)
    private Reader reader;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SPECIFIC_BOOK_ID", nullable = false)
    private SpecificBook specificBook;

    public SpecificBookReader() {
    }

    public SpecificBookReader(Long id, Boolean isReturn, Reader reader, SpecificBook specificBook) {
        this.id = id;
        this.isReturn = isReturn;
        this.reader = reader;
        this.specificBook = specificBook;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getReturn() {
        return isReturn;
    }

    public void setReturn(Boolean aReturn) {
        isReturn = aReturn;
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
}
