package com.jcrdev.franquicias_test.infrastructure.persistence.adapter;

import com.jcrdev.franquicias_test.application.port.out.FranchiseRepositoryPort;
import com.jcrdev.franquicias_test.domain.model.Franchise;
import com.jcrdev.franquicias_test.infrastructure.persistence.entity.FranchiseEntity;
import com.jcrdev.franquicias_test.infrastructure.persistence.repository.SpringFranchiseRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class FranchiseRepositoryAdapter implements FranchiseRepositoryPort {

    private final SpringFranchiseRepository repository;

    public FranchiseRepositoryAdapter(SpringFranchiseRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        return repository.save(toEntity(franchise)).map(this::toDomain);
    }

    @Override
    public Mono<Franchise> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<Franchise> updateName(UUID id, String name) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setName(name);
                    return repository.save(entity);
                })
                .map(this::toDomain);
    }

    private FranchiseEntity toEntity(Franchise franchise) {
        return new FranchiseEntity(franchise.id(), franchise.name(), franchise.createdAt());
    }

    private Franchise toDomain(FranchiseEntity entity) {
        return new Franchise(entity.getId(), entity.getName(), entity.getCreatedAt());
    }
}
