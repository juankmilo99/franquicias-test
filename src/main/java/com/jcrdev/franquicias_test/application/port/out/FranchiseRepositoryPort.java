package com.jcrdev.franquicias_test.application.port.out;

import com.jcrdev.franquicias_test.domain.model.Franchise;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface FranchiseRepositoryPort {

    Mono<Franchise> save(Franchise franchise);

    Mono<Franchise> findById(UUID id);

    Mono<Franchise> updateName(UUID id, String name);
}
