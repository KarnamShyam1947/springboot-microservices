package com.shyam.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.shyam.dto.BookResponse;

@FeignClient(name = "book-service")
public interface BookClient {

    @GetMapping("/api/v1/books/author/{id}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(
        @PathVariable("id") long authorId
    );

}
