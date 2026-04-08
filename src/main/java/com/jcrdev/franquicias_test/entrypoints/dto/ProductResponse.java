package com.jcrdev.franquicias_test.entrypoints.dto;

import java.util.UUID;

public record ProductResponse(UUID id, String name, Integer stock, UUID branchId) {
}
