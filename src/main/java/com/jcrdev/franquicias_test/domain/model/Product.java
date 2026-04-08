package com.jcrdev.franquicias_test.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Product(UUID id, String name, Integer stock, UUID branchId, LocalDateTime createdAt) {
}
