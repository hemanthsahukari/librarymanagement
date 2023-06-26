package com.librarymanagement.service;

import com.librarymanagement.model.BorrowedBook;
import com.librarymanagement.repository.BorrowedBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BorrowedBookService {
    private final BorrowedBookRepository borrowedBookRepository;

    @Autowired
    public BorrowedBookService(BorrowedBookRepository borrowedBookRepository) {
        this.borrowedBookRepository = borrowedBookRepository;
    }

    public List<BorrowedBook> getAllBorrowedBooks() {
        return borrowedBookRepository.findAll();
    }

    public Optional<BorrowedBook> getBorrowedBookById(Long id) {
        return borrowedBookRepository.findById(id);
    }

    public BorrowedBook saveBorrowedBook(BorrowedBook borrowedBook) {
        return borrowedBookRepository.save(borrowedBook);
    }

    public void deleteBorrowedBook(Long id) {
        borrowedBookRepository.deleteById(id);
    }
}