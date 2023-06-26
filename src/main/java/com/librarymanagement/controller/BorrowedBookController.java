package com.librarymanagement.controller;

import com.librarymanagement.model.BorrowedBook;
import com.librarymanagement.service.BorrowedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/borrowed-books")
public class BorrowedBookController {
    private final BorrowedBookService borrowedBookService;

    @Autowired
    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        this.borrowedBookService = borrowedBookService;
    }

    @GetMapping("/getAllBorrowedBooks")
    public ResponseEntity<List<BorrowedBook>> getAllBorrowedBooks() {
        List<BorrowedBook> borrowedBooks = borrowedBookService.getAllBorrowedBooks();
        return new ResponseEntity<>(borrowedBooks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BorrowedBook> getBorrowedBookById(@PathVariable Long id) {
        Optional<BorrowedBook> borrowedBook = borrowedBookService.getBorrowedBookById(id);
        return borrowedBook.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/createBorrowedbook")
    public ResponseEntity<BorrowedBook> createBorrowedBook(@RequestBody BorrowedBook borrowedBook) {
        BorrowedBook savedBorrowedBook = borrowedBookService.saveBorrowedBook(borrowedBook);
        return new ResponseEntity<>(savedBorrowedBook, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<BorrowedBook> updateBorrowedBook(@PathVariable Long id, @RequestBody BorrowedBook borrowedBook) {
        Optional<BorrowedBook> existingBorrowedBook = borrowedBookService.getBorrowedBookById(id);
        if (existingBorrowedBook.isPresent()) {
            borrowedBook.setId(id);
            BorrowedBook updatedBorrowedBook = borrowedBookService.saveBorrowedBook(borrowedBook);
            return new ResponseEntity<>(updatedBorrowedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrowedBook(@PathVariable Long id) {
        Optional<BorrowedBook> borrowedBook = borrowedBookService.getBorrowedBookById(id);
        if (borrowedBook.isPresent()) {
            borrowedBookService.deleteBorrowedBook(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}