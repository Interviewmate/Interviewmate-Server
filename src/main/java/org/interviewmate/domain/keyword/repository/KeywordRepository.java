package org.interviewmate.domain.keyword.repository;

import java.util.Optional;
import org.interviewmate.domain.keyword.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findByName(String Keyword);

}
