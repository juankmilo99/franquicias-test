package com.jcrdev.franquicias_test.infrastructure.persistence.adapter;

import com.jcrdev.franquicias_test.application.port.out.BranchRepositoryPort;
import com.jcrdev.franquicias_test.domain.model.Branch;
import com.jcrdev.franquicias_test.infrastructure.persistence.entity.BranchEntity;
import com.jcrdev.franquicias_test.infrastructure.persistence.repository.SpringBranchRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class BranchRepositoryAdapter implements BranchRepositoryPort {

    private final SpringBranchRepository repository;

    public BranchRepositoryAdapter(SpringBranchRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Branch> save(Branch branch) {
        return repository.save(toEntity(branch)).map(this::toDomain);
    }

    @Override
    public Mono<Branch> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Flux<Branch> findByFranchiseId(UUID franchiseId) {
        return repository.findByFranchiseId(franchiseId).map(this::toDomain);
    }

    @Override
    public Mono<Branch> updateName(UUID id, String name) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setName(name);
                    return repository.save(entity);
                })
                .map(this::toDomain);
    }

    private BranchEntity toEntity(Branch branch) {
        return new BranchEntity(branch.id(), branch.name(), branch.franchiseId(), branch.createdAt());
    }

    private Branch toDomain(BranchEntity entity) {
        return new Branch(entity.getId(), entity.getName(), entity.getFranchiseId(), entity.getCreatedAt());
    }
}
