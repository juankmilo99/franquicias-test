package com.jcrdev.franquicias_test.application.port.out;

import com.jcrdev.franquicias_test.domain.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BranchRepositoryPort {

    Mono<Branch> save(Branch branch);

    Mono<Branch> findById(UUID id);

    Flux<Branch> findByFranchiseId(UUID franchiseId);

    Mono<Branch> updateName(UUID id, String name);
}
