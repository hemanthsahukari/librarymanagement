package com.librarymanagement.service;

import com.librarymanagement.exception.BookNotFoundException;
import com.librarymanagement.exception.BorrowedBookNotFoundException;
import com.librarymanagement.exception.StudentNotFoundException;
import com.librarymanagement.model.Book;
import com.librarymanagement.model.BorrowedBook;
import com.librarymanagement.model.Student;
import com.librarymanagement.repository.BookRepository;
import com.librarymanagement.repository.BorrowedBookRepository;
import com.librarymanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BorrowedBookRepository borrowedBookRepository;
    private final StudentRepository studentRepository;

   // @Autowired
    public BookService(BookRepository bookRepository, BorrowedBookRepository borrowedBookRepository, StudentRepository studentRepository) {
        this.bookRepository = bookRepository;
        this.borrowedBookRepository = borrowedBookRepository;
        this.studentRepository = studentRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookRepository.findAll();
    }

    public Optional<BorrowedBook> getBorrowedBookById(Long id) {
        return borrowedBookRepository.findById(id);
    }

    public BorrowedBook borrowBook(Long bookId, Long studentId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));

        if (book.isAvailable()) {
            book.setAvailable(false);
            bookRepository.save(book);

            LocalDate borrowDate = LocalDate.now();
            LocalDate returnDate = borrowDate.plusDays(14);

            BorrowedBook borrowedBook = new BorrowedBook(book, student, borrowDate, returnDate, false);
            return borrowedBookRepository.save(borrowedBook);
        } else {
            throw new IllegalStateException("Book is not available for borrowing");
        }
    }

    public void returnBook(Long borrowedBookId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findById(borrowedBookId)
                .orElseThrow(() -> new BorrowedBookNotFoundException(borrowedBookId));

        if (!borrowedBook.isReturned()) {
            borrowedBook.setReturned(true);
            borrowedBookRepository.save(borrowedBook);

            Book book = borrowedBook.getBook();
            book.setAvailable(true);
            bookRepository.save(book);
        } else {
            throw new IllegalStateException("Book has already been returned");
        }
    }

    public void calculateFineForOverdueBooks() {
        List<BorrowedBook> overdueBooks = borrowedBookRepository.findByReturnDateBeforeAndReturnedFalse(LocalDate.now());


        for (BorrowedBook borrowedBook : overdueBooks) {
            if (!borrowedBook.isReturned()) {
                LocalDate today = LocalDate.now();
                LocalDate returnDate = borrowedBook.getReturnDate();

                long daysOverdue = ChronoUnit.DAYS.between(returnDate, today);
                double fineAmount = daysOverdue * 1.0;

                Student student = borrowedBook.getStudent();
                double currentFineAmount = student.getFineAmount();
                double updatedFineAmount = currentFineAmount + fineAmount;
                student.setFineAmount(updatedFineAmount);
                studentRepository.save(student);
                borrowedBook.setFineCollected(true);
                borrowedBookRepository.save(borrowedBook);
            }
        }
    }
}
