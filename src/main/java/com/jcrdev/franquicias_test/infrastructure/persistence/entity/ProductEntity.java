package com.jcrdev.franquicias_test.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(schema = "franchise_app", name = "products")
public class ProductEntity {

    @Id
    private UUID id;
    private String name;
    private Integer stock;

    @Column("branch_id")
    private UUID branchId;

    private LocalDateTime createdAt;

    public ProductEntity() {
    }

    public ProductEntity(UUID id, String name, Integer stock, UUID branchId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.branchId = branchId;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public UUID getBranchId() {
        return branchId;
    }

    public void setBranchId(UUID branchId) {
        this.branchId = branchId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
