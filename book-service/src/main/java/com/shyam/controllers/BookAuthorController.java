package com.shyam.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.BookAuthor;
import com.shyam.entities.BookEntity;
import com.shyam.exceptions.BookNotFoundException;
import com.shyam.services.BookService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books/author")
public class BookAuthorController {

    private final BookService bookService;
    
    @GetMapping("/{id}")
    public ResponseEntity<List<BookEntity>> addAuthor(
        @PathVariable("id") long authorId
    ) throws BookNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(bookService.getBooksByAuthorId(authorId));
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<BookEntity> addAuthor(
        @RequestBody BookAuthor author
    ) throws BookNotFoundException {
        
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(bookService.addAuthor(author.getBookId(), author.getAuthorId()));
    }
    
    @DeleteMapping("/")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<BookEntity> deleteAuthor(
        @RequestBody BookAuthor author
    ) throws BookNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(bookService.deleteAuthor(author.getBookId(), author.getAuthorId()));
    }
}
