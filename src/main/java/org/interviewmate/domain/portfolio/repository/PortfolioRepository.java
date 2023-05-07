package org.interviewmate.domain.portfolio.repository;

import org.interviewmate.domain.portfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
}
