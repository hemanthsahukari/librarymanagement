package com.librarymanagement.repository;

import com.librarymanagement.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByReturnDateBeforeAndReturnedFalse(LocalDate currentDate);
}