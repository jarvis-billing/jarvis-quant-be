package com.megabloques.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterProductionRequest {

    @NotBlank(message = "productId is required")
    private String productId;

    private String productName;

    @NotNull(message = "cementBags is required")
    @Min(value = 1, message = "cementBags must be at least 1")
    private Integer cementBags;

    @NotNull(message = "unitsPerBag is required")
    @Min(value = 1, message = "unitsPerBag must be at least 1")
    private Integer unitsPerBag;

    @NotNull(message = "kgPerBag is required")
    @Min(value = 1, message = "kgPerBag must be positive")
    private Double kgPerBag;

    @NotNull(message = "productionCostPerBatch is required")
    private Double productionCostPerBatch;

    private String notes;
}
