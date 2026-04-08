package com.jcrdev.franquicias_test.infrastructure.persistence.repository;

import com.jcrdev.franquicias_test.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface SpringProductRepository extends ReactiveCrudRepository<ProductEntity, UUID> {

    Mono<ProductEntity> findByIdAndBranchId(UUID id, UUID branchId);

    @Query("SELECT * FROM franchise_app.products WHERE branch_id = :branchId ORDER BY stock DESC LIMIT 1")
    Mono<ProductEntity> findTopByBranchId(@Param("branchId") UUID branchId);
}
