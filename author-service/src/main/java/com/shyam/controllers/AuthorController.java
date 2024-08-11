package com.shyam.controllers;

import java.util.Set;
import java.util.Map;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shyam.dto.AuthorRequest;
import com.shyam.dto.AuthorResponse;
import com.shyam.entities.AuthorEntity;
import com.shyam.exceptions.AuthorExistsException;
import com.shyam.exceptions.AuthorNotFoundException;
import com.shyam.services.AuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class AuthorController {
    
    private final AuthorService authorService;

    @PostMapping("/")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<AuthorEntity> addAuthor(
        @Valid @RequestBody AuthorRequest request
    ) throws AuthorExistsException {
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(authorService.addAuthor(request));
    }

    @GetMapping("/")
    public ResponseEntity<List<AuthorEntity>> getAllAuthors() {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(authorService.getAuthors());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getAuthor(
        @PathVariable("id") long id
    ) throws AuthorNotFoundException {
        
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(authorService.getCompleteAuthor(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<AuthorEntity> updateAuthor(
        @PathVariable("id") long id, 
        @RequestBody AuthorRequest request
    ) throws AuthorNotFoundException {

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(authorService.updateAuthor(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('client_admin')")
    public ResponseEntity<Map<String, String>> deleteAuthor(
        @PathVariable("id") long id
    ) throws AuthorNotFoundException {

        authorService.deleteAuthor(id);

        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(Map.of("message", "deleted successfully"));
    }

    @GetMapping("/details")
    public List<AuthorEntity> getAuthorDetails(
        @RequestParam("author-id") Set<Long> authorIds
    ) {
        return authorService.getAuthorDetails(authorIds);
    }
}
