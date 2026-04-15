package com.megabloques.controller;

import com.megabloques.document.ProductionBatch;
import com.megabloques.dto.QualityUpdateRequest;
import com.megabloques.dto.RegisterProductionRequest;
import com.megabloques.service.ProductionBatchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/production-batches")
@RequiredArgsConstructor
public class ProductionBatchController {

    private final ProductionBatchService productionBatchService;

    @GetMapping
    public ResponseEntity<List<ProductionBatch>> findAll(
            @RequestParam(required = false) String productId) {
        return ResponseEntity.ok(productionBatchService.findAll(productId));
    }

    @PostMapping
    public ResponseEntity<ProductionBatch> register(
            @Valid @RequestBody RegisterProductionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productionBatchService.register(request));
    }

    @PatchMapping("/{id}/quality")
    public ResponseEntity<ProductionBatch> updateQualityStatus(
            @PathVariable String id,
            @Valid @RequestBody QualityUpdateRequest request) {
        return ResponseEntity.ok(productionBatchService.updateQualityStatus(id, request.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productionBatchService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
