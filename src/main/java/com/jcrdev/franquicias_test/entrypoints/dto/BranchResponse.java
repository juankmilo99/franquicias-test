package com.jcrdev.franquicias_test.entrypoints.dto;

import java.util.UUID;

public record BranchResponse(UUID id, String name, UUID franchiseId) {
}
