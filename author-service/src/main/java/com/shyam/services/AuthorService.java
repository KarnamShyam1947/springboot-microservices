package com.shyam.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shyam.dto.AuthorRequest;
import com.shyam.entities.AuthorEntity;
import com.shyam.exceptions.AuthorExistsException;
import com.shyam.exceptions.AuthorNotFoundException;
import com.shyam.repositories.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
    
    private final AuthorRepository authorRepository;

    public AuthorEntity addAuthor(AuthorRequest request) throws AuthorExistsException {
        //TODO: refactor with ModelMapper

        AuthorEntity author = authorRepository.findByName(request.getName());
        
        if (author != null)
            throw new AuthorExistsException("Author already exists with name : " + request.getName());
        

        AuthorEntity authorEntity = AuthorEntity
                                    .builder()
                                    .name(request.getName())
                                    .address(request.getAddress())
                                    .description(request.getDescription())
                                    .build();

        return authorRepository.save(authorEntity);
    }

    public List<AuthorEntity> getAuthors() {
        return authorRepository.findAll();
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
        // TODO: delete all books of author

        AuthorEntity author = getAuthor(id);
        authorRepository.delete(author);
    }

}
