package com.shyam.controllers;

import java.util.Map;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.BookAuthor;
import com.shyam.dto.BookRequest;
import com.shyam.dto.BookStock;
import com.shyam.dto.BookUpdateRequest;
import com.shyam.entities.BookEntity;
import com.shyam.services.BookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/")
    public BookEntity addBook(@RequestBody BookRequest request) {
        return bookService.addBook(request);
    }
    
    @GetMapping("/")
    public List<BookEntity> getBooks() {
        return bookService.getBooks();
    }
    
    @GetMapping("/{id}")
    public BookEntity addBook(@PathVariable("id") long id) {
        return bookService.getBook(id);
    }

    @PutMapping("/{id}")
    public BookEntity updateBook(
        @PathVariable("id") long id,
        @RequestBody BookUpdateRequest request
    ) {
        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> deleteBook(@PathVariable("id") long id) {
        bookService.deleteBook(id);
        return Map.of("message", "Deleted successfully");
    }

    @PostMapping("/stock")
    public BookEntity updateStock(
        @RequestBody BookStock stock
    ) {
        return bookService.updateStock(stock.getBookId(), stock.getStock());
    }

    @PostMapping("/author")
    public BookEntity addAuthor(
        @RequestBody BookAuthor author
    ) {
        System.out.println(author);
        return bookService.addAuthor(author.getBookId(), author.getAuthorId());
    }
    
    @DeleteMapping("/author")
    public BookEntity deleteAuthor(
        @RequestBody BookAuthor author
    ) {
        return bookService.deleteAuthor(author.getBookId(), author.getAuthorId());
    }

}