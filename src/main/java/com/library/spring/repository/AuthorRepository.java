package com.library.spring.repository;

import com.library.spring.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findOneByAuthorName(String authorName);
}
