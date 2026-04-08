package com.jcrdev.franquicias_test.infrastructure.persistence.adapter;

import com.jcrdev.franquicias_test.application.port.out.ProductRepositoryPort;
import com.jcrdev.franquicias_test.domain.model.Product;
import com.jcrdev.franquicias_test.infrastructure.persistence.entity.ProductEntity;
import com.jcrdev.franquicias_test.infrastructure.persistence.repository.SpringProductRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final SpringProductRepository repository;

    public ProductRepositoryAdapter(SpringProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<Product> save(Product product) {
        return repository.save(toEntity(product)).map(this::toDomain);
    }

    @Override
    public Mono<Product> findById(UUID id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Mono<Product> findByIdAndBranchId(UUID id, UUID branchId) {
        return repository.findByIdAndBranchId(id, branchId).map(this::toDomain);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return repository.deleteById(id);
    }

    @Override
    public Mono<Product> findTopByBranchId(UUID branchId) {
        return repository.findTopByBranchId(branchId).map(this::toDomain);
    }

    @Override
    public Mono<Product> updateStock(UUID id, Integer stock) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setStock(stock);
                    return repository.save(entity);
                })
                .map(this::toDomain);
    }

    @Override
    public Mono<Product> updateName(UUID id, String name) {
        return repository.findById(id)
                .flatMap(entity -> {
                    entity.setName(name);
                    return repository.save(entity);
                })
                .map(this::toDomain);
    }

    private ProductEntity toEntity(Product product) {
        return new ProductEntity(product.id(), product.name(), product.stock(), product.branchId(), product.createdAt());
    }

    private Product toDomain(ProductEntity entity) {
        return new Product(entity.getId(), entity.getName(), entity.getStock(), entity.getBranchId(), entity.getCreatedAt());
    }
}
