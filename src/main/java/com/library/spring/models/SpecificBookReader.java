package com.library.spring.models;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table(name = "SPECIFIC_BOOK_READER")
public class SpecificBookReader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotFound(action = NotFoundAction.IGNORE)
    @Column(name="ID")
    private Long id;

    @Column(name="IS_RETURN")
    @NotNull
    private Boolean isReturn;

    @Column(name = "DATE_OF_ISSUE")
    @PastOrPresent(message = "date Of Issue must be present or past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "dateOfIssue should not be null")
    private LocalDate dateOfIssue;

    @Column(name = "RETURN_DATE")
    @Future(message = "return Date must be in future")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "returnDate should not be null")
    private LocalDate returnDate;

    @Column(name = "REAL_RETURN_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate realReturnDate;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "READER_ID", nullable = false)
    private Reader reader;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SPECIFIC_BOOK_ID", nullable = false)
    private SpecificBook specificBook;

    public SpecificBookReader() {
    }

    public SpecificBookReader(Long id, Boolean isReturn, LocalDate dateOfIssue,
                              LocalDate returnDate, LocalDate realReturnDate, Reader reader,
                              SpecificBook specificBook) {
        this.id = id;
        this.isReturn = isReturn;
        this.dateOfIssue = dateOfIssue;
        this.returnDate = returnDate;
        this.realReturnDate = realReturnDate;
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

    public LocalDate getRealReturnDate() {
        return realReturnDate;
    }

    public void setRealReturnDate(LocalDate realReturnDate) {
        this.realReturnDate = realReturnDate;
    }
}
