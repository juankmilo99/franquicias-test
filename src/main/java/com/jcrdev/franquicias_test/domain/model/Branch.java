package com.jcrdev.franquicias_test.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Branch(UUID id, String name, UUID franchiseId, LocalDateTime createdAt) {
}
