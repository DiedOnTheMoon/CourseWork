package com.library.spring.repository;

import com.library.spring.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher findPublisherByPublisherName(String publisherName);
}
