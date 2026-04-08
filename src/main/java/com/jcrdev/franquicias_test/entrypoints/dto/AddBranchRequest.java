package com.jcrdev.franquicias_test.entrypoints.dto;

import jakarta.validation.constraints.NotBlank;

public record AddBranchRequest(@NotBlank(message = "El nombre es obligatorio") String name) {
}
