package com.shyam.clients;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.shyam.dto.response.AuthorDTO;

@FeignClient(name = "author-service")
public interface AuthorClient {

    @GetMapping("/api/v1/author/details")
    public List<AuthorDTO> getAuthorDetails(
        @RequestParam("author-id") Set<Long> authorIds
    );
    
    @GetMapping("/api/v1/author/{id}")
    public List<AuthorDTO> getAuthorDetails(
        @PathVariable("id") long authorId
    );

}
