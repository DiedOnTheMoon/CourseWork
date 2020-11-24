package com.library.spring.repository;

import com.library.spring.models.SpecificBookReader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificBookReaderRepository extends JpaRepository<SpecificBookReader, Long> {
}
