package com.jcrdev.franquicias_test.application.model;

import java.util.UUID;

public record BranchTopProduct(UUID branchId, String branchName, UUID productId, String productName, Integer stock) {
}
