package org.interviewmate.domain.userkeyword.repository;

import org.interviewmate.domain.userkeyword.model.UserKeyword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {
}
