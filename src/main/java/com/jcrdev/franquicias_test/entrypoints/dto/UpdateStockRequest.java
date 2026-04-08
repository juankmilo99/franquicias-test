package com.jcrdev.franquicias_test.entrypoints.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateStockRequest(
        @NotNull(message = "El stock es obligatorio") @Min(value = 0, message = "El stock debe ser >= 0") Integer stock
) {
}
