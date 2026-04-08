package com.jcrdev.franquicias_test.infrastructure.persistence.repository;

import com.jcrdev.franquicias_test.infrastructure.persistence.entity.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface SpringBranchRepository extends ReactiveCrudRepository<BranchEntity, UUID> {

    Flux<BranchEntity> findByFranchiseId(UUID franchiseId);
}
