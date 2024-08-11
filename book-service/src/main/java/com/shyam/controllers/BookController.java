package com.shyam.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.BookRequest;
import com.shyam.dto.BookStock;
import com.shyam.dto.BookUpdateRequest;
import com.shyam.dto.response.BookResponse;
import com.shyam.entities.BookEntity;
import com.shyam.exceptions.AuthorNotFoundException;
import com.shyam.exceptions.BookNotFoundException;
import com.shyam.services.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<BookEntity> addBook(@RequestBody BookRequest request) throws AuthorNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(bookService.addBook(request));
    }
    
    @GetMapping("/")
    public ResponseEntity<List<BookEntity>> getBooks() {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(bookService.getBooks());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> addBook(
        @PathVariable("id") long id
    ) throws BookNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(bookService.getCompleteBookDetails(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<BookEntity> updateBook(
        @PathVariable("id") long id,
        @RequestBody BookUpdateRequest request
    ) throws BookNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable("id") long id) throws BookNotFoundException {
        bookService.deleteBook(id);
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "Deleted successfully"));
    }

    @PostMapping("/stock")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<BookEntity> updateStock(
        @RequestBody BookStock stock
    ) throws BookNotFoundException {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(bookService.updateStock(stock.getBookId(), stock.getStock()));
    }

}