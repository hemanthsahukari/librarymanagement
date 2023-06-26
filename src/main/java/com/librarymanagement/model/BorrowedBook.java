package com.librarymanagement.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "borrowed_books")
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned;
    private boolean fineCollected;

    public BorrowedBook() {
    }

    public BorrowedBook(Book book, Student student, LocalDate borrowDate, LocalDate returnDate, boolean returned) {
        this.book = book;
        this.student = student;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.returned = returned;
        this.fineCollected = false;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public boolean isFineCollected() {
        return fineCollected;
    }

    public void setFineCollected(boolean fineCollected) {
        this.fineCollected = fineCollected;
    }
}