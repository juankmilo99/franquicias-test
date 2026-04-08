package com.jcrdev.franquicias_test.infrastructure.persistence.repository;

import com.jcrdev.franquicias_test.infrastructure.persistence.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface SpringFranchiseRepository extends ReactiveCrudRepository<FranchiseEntity, UUID> {
}
