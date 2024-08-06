package com.shyam.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shyam.entities.BookEntity;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
    @Query("SELECT b FROM BookEntity b JOIN b.authorIds a WHERE a = :authorId")
    List<BookEntity> findBooksByAuthorId(@Param("authorId") Long authorId);
}
