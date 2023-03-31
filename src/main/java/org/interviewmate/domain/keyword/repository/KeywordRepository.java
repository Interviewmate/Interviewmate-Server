package org.interviewmate.domain.keyword.repository;

import org.interviewmate.domain.keyword.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Keyword findByName(String Keyword);

}
