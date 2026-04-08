package com.jcrdev.franquicias_test.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record Franchise(UUID id, String name, LocalDateTime createdAt) {
}
