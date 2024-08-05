package com.shyam.services;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.shyam.dto.BookRequest;
import com.shyam.dto.BookUpdateRequest;
import com.shyam.entities.BookEntity;
import com.shyam.repositories.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final ModelMapper modelMapper;
    private final BookRepository bookRepository;

    public BookEntity addBook(BookRequest request) {
        BookEntity book = modelMapper.map(request, BookEntity.class);
        // TODO: check valid author or not

        return bookRepository.save(book);
    }

    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }
    
    public BookEntity getBook(long id) {
        return bookRepository
                .findById(id)
                .orElseThrow(
                    () -> new RuntimeException("Book Not Found")
                );
    }

    public void deleteBook(long id) {
        BookEntity book = getBook(id);
        bookRepository.delete(book);
    }

    public BookEntity updateBook(long id, BookUpdateRequest request) {
        BookEntity book = getBook(id);

        if (request.getName() != null) 
            book.setName(request.getName());
        
        if (request.getCategory() != null) 
            book.setCategory(request.getCategory());
        
        if (request.getDescription() != null) 
            book.setDescription(request.getDescription());
        
        return bookRepository.save(book);
    }

    public BookEntity updateStock(long id, int stock) {
        BookEntity book = getBook(id);
        book.setStock(stock);

        return bookRepository.save(book);
    }

    public BookEntity addAuthor(long id, long authorId){
        BookEntity book = getBook(id);
        Set<Long> authorIds = book.getAuthorIds();

        authorIds.add(authorId);

        return bookRepository.save(book);
    }
    
    public BookEntity deleteAuthor(long id, long authorId){
        BookEntity book = getBook(id);
        Set<Long> authorIds = book.getAuthorIds();

        authorIds.remove(authorId);

        return bookRepository.save(book);
    }

}
