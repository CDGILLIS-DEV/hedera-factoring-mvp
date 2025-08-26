package com.carlgillis.hedera_factoring.repository;

import com.carlgillis.hedera_factoring.domain.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {
}
