package com.library.spring.repository;

import com.library.spring.models.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    Librarian findByUsername(String username);
}