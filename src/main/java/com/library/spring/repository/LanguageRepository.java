package com.library.spring.repository;

import com.library.spring.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    Language findLanguageByLanguageName(String languageName);
}
