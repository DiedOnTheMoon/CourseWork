package com.library.spring.repository;

import com.library.spring.models.Blacklist;
import com.library.spring.models.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    Set<Blacklist> findAllByReaderAndSpecificBook_BookBookNameStartsWith(Reader reader, String bookName);
}
