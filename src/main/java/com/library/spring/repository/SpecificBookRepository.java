package com.library.spring.repository;

import com.library.spring.models.SpecificBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecificBookRepository extends JpaRepository<SpecificBook, Long> {
}
