package com.jcrdev.franquicias_test.entrypoints.dto;

import java.util.UUID;

public record TopStockProductResponse(
        UUID branchId,
        String branchName,
        UUID productId,
        String productName,
        Integer stock
) {
}
