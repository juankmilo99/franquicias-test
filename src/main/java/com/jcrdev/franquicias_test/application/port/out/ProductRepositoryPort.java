package com.jcrdev.franquicias_test.application.port.out;

import com.jcrdev.franquicias_test.domain.model.Product;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductRepositoryPort {

    Mono<Product> save(Product product);

    Mono<Product> findById(UUID id);

    Mono<Product> findByIdAndBranchId(UUID id, UUID branchId);

    Mono<Void> deleteById(UUID id);

    Mono<Product> findTopByBranchId(UUID branchId);

    Mono<Product> updateStock(UUID id, Integer stock);

    Mono<Product> updateName(UUID id, String name);
}
