package org.interviewmate.domain.user.repository;

import org.interviewmate.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
