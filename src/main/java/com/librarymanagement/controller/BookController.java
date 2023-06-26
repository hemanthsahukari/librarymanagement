package com.librarymanagement.controller;

import com.librarymanagement.model.Book;
import com.librarymanagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    @Autowired
    private BookService bookService;


    @GetMapping("/getAllBooks")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createBook")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.getBookById(id)
                .map(existingBook -> {
                    existingBook.setTitle(book.getTitle());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setGenre(book.getGenre());
                    Book updatedBook = bookService.saveBook(existingBook);
                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{bookId}/borrow/{studentId}")
    public ResponseEntity<Void> borrowBook(@PathVariable Long bookId, @PathVariable Long studentId) {
        bookService.borrowBook(bookId, studentId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{borrowedBookId}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long borrowedBookId) {
        bookService.returnBook(borrowedBookId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calculate-fine")
    public ResponseEntity<Void> calculateFineForOverdueBooks() {
        bookService.calculateFineForOverdueBooks();
        return ResponseEntity.ok().build();
    }
}