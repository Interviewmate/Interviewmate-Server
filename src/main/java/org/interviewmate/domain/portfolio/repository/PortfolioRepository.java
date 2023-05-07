package org.interviewmate.domain.portfolio.repository;

import org.interviewmate.domain.portfolio.model.Portfolio;
import org.interviewmate.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Optional<Portfolio> findByUser(User user);
}
