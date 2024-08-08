package com.shyam.services;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
// import org.springframework.web.client.RestTemplate;

import com.shyam.clients.BookClient;
import com.shyam.dto.AuthorRequest;
import com.shyam.dto.AuthorResponse;
import com.shyam.entities.AuthorEntity;
import com.shyam.exceptions.AuthorExistsException;
import com.shyam.exceptions.AuthorNotFoundException;
import com.shyam.repositories.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
    
    private final AuthorRepository authorRepository;
    // private final RestTemplate restTemplate;
    private final BookClient bookClient;
    private final ModelMapper mapper;

    public AuthorEntity addAuthor(AuthorRequest request) throws AuthorExistsException {

        AuthorEntity author = authorRepository.findByName(request.getName());
        
        if (author != null)
            throw new AuthorExistsException("Author already exists with name : " + request.getName());
        

        AuthorEntity authorEntity = mapper.map(author, AuthorEntity.class);

        return authorRepository.save(authorEntity);
    }

    public List<AuthorEntity> getAuthors() {
        return authorRepository.findAll();
    }

    public AuthorResponse getCompleteAuthor(long id) throws AuthorNotFoundException {
        AuthorEntity author = getAuthor(id);
        AuthorResponse authorResponse = mapper.map(author, AuthorResponse.class);

        authorResponse.setBooks(bookClient.getBooksByAuthor(id).getBody());

        return authorResponse;
    }

    public AuthorEntity getAuthor(long id) throws AuthorNotFoundException {
        return authorRepository
                .findById(id)
                .orElseThrow(
                    () -> new AuthorNotFoundException("Author Not Found With id : " + id)
                );
    }

    public AuthorEntity updateAuthor(long id, AuthorRequest request) throws AuthorNotFoundException {
        AuthorEntity author = getAuthor(id);
        System.out.println(request);

        if(request.getName() != null)  
            author.setName(request.getName());

        if (request.getAddress() != null) 
            author.setAddress(request.getAddress());
        
        if (request.getDescription() != null) 
            author.setDescription(request.getDescription());

        System.out.println(author);
        return authorRepository.save(author);
    }

    public void deleteAuthor(long id) throws AuthorNotFoundException {
        // TODO: delete all books of authora

        AuthorEntity author = getAuthor(id);
        authorRepository.delete(author);
    }

    public List<AuthorEntity> getAuthorDetails(Set<Long> authorIds) {
        return authorRepository.getAuthorDetails(authorIds);
    }

    // public List<BookResponse> getBooks(long id) {
    //     ResponseEntity<List<BookResponse>> entity = restTemplate.exchange(
    //         "/author/{id}", 
    //         HttpMethod.GET,
    //         null, 
    //         new ParameterizedTypeReference<List<BookResponse>>() {},
    //         id
    //     );

    //     return entity.getBody();
    // }

}
