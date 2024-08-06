package com.shyam.repositories;

import java.util.Set;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shyam.entities.AuthorEntity;


@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    AuthorEntity findByName(String name);

    @Query(value = "FROM AuthorEntity a WHERE a.id IN :authorIds")
    public List<AuthorEntity> getAuthorDetails(@Param("authorIds") Set<Long> authorIds);
}
