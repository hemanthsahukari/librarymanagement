package com.librarymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BorrowedBookNotFoundException extends RuntimeException {
    public BorrowedBookNotFoundException(Long id) {
        super("Borrowed book not found with ID: " + id);
    }
}