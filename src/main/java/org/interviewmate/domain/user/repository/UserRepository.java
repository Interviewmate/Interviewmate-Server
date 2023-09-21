package org.interviewmate.domain.user.repository;

import java.util.Optional;
import org.interviewmate.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByNickName(String nickName);
    Optional<User> findByEmail(String email);

}
