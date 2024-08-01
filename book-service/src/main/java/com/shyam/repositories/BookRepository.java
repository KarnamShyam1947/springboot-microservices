package com.shyam.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shyam.entities.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    
}
