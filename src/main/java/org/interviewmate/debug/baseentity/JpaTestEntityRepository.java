package org.interviewmate.debug.baseentity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTestEntityRepository extends JpaRepository<TestEntity, Long> {
}
