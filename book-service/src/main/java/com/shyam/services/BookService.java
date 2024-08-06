package com.shyam.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.shyam.dto.BookRequest;
import com.shyam.dto.BookUpdateRequest;
import com.shyam.dto.response.AuthorDTO;
import com.shyam.dto.response.BookResponse;
import com.shyam.entities.BookEntity;
import com.shyam.exceptions.AuthorNotFoundException;
import com.shyam.exceptions.BookNotFoundException;
import com.shyam.repositories.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    @Autowired(required = true)
    @Qualifier(value = "authorServiceRestTemplate")
    private RestTemplate authorServiceRestTemplate;

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public BookEntity addBook(BookRequest request) throws AuthorNotFoundException  {
        BookEntity book = modelMapper.map(request, BookEntity.class);

        for (long authorId : request.getAuthorIds()) {
            try {
                authorServiceRestTemplate.getForEntity(
                        "/{id}",
                        AuthorDTO.class,
                        authorId
                );
            } 
            catch(HttpClientErrorException e) {
                // System.out.println("\nI am here\n");
                throw new AuthorNotFoundException();
            }
            catch (Exception e) {
                System.out.println("Unknown Exception");
            }

        }

        return bookRepository.save(book);
    }

    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    public BookEntity getBook(long id) throws BookNotFoundException {
        return bookRepository
                .findById(id)
                .orElseThrow(
                    () -> new BookNotFoundException("Book Not Found with id : " + id)
                );
    }
    
    public BookResponse getCompleteBookDetails(long id) throws BookNotFoundException {
        BookEntity bookEntity = bookRepository
                                .findById(id)
                                .orElseThrow(
                                    () -> new BookNotFoundException("Book Not Found with id : " + id)
                                );

        BookResponse bookResponse = modelMapper.map(bookEntity, BookResponse.class);
        bookResponse.setAuthors(getAuthorDetails(bookEntity.getAuthorIds()));

        return bookResponse;
    }

    public void deleteBook(long id) throws BookNotFoundException {
        BookEntity book = getBook(id);
        bookRepository.delete(book);
    }

    public BookEntity updateBook(long id, BookUpdateRequest request) throws BookNotFoundException {
        BookEntity book = getBook(id);

        if (request.getName() != null) 
            book.setName(request.getName());
        
        if (request.getCategory() != null) 
            book.setCategory(request.getCategory());
        
        if (request.getDescription() != null) 
            book.setDescription(request.getDescription());
        
        return bookRepository.save(book);
    }

    public BookEntity updateStock(long id, int stock) throws BookNotFoundException {
        BookEntity book = getBook(id);
        book.setStock(stock);

        return bookRepository.save(book);
    }

    public BookEntity addAuthor(long id, long authorId) throws BookNotFoundException{
        BookEntity book = getBook(id);
        Set<Long> authorIds = book.getAuthorIds();

        authorIds.add(authorId);

        return bookRepository.save(book);
    }
    
    public BookEntity deleteAuthor(long id, long authorId) throws BookNotFoundException{
        BookEntity book = getBook(id);
        Set<Long> authorIds = book.getAuthorIds();

        authorIds.remove(authorId);

        return bookRepository.save(book);
    }

    public List<BookEntity> getBooksByAuthorId(long authorId) {
        return bookRepository.findBooksByAuthorId(authorId);
    }

    public List<AuthorDTO> getAuthorDetails(Set<Long> authorIds) {
        String authorIdsParam = authorIds.stream()
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(","));

        String url = String.format("/details?author-id=%s", authorIdsParam);

        ResponseEntity<List<AuthorDTO>> response = authorServiceRestTemplate.exchange(
                                                        url,
                                                        HttpMethod.GET,
                                                        null,
                                                        new ParameterizedTypeReference<List<AuthorDTO>>() {}
                                                    );

        return response.getBody();
    }

}
