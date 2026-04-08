package com.jcrdev.franquicias_test.infrastructure.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(schema = "franchise_app", name = "branches")
public class BranchEntity {

    @Id
    private UUID id;
    private String name;

    @Column("franchise_id")
    private UUID franchiseId;

    private LocalDateTime createdAt;

    public BranchEntity() {
    }

    public BranchEntity(UUID id, String name, UUID franchiseId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.franchiseId = franchiseId;
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

    public UUID getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(UUID franchiseId) {
        this.franchiseId = franchiseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
