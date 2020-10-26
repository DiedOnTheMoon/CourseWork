package com.library.spring.repository;

import com.library.spring.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language,Long > {
}
