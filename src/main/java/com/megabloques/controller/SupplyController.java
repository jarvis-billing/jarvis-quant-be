package com.megabloques.controller;

import com.megabloques.document.Supply;
import com.megabloques.dto.StockUpdateRequest;
import com.megabloques.service.SupplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplies")
@RequiredArgsConstructor
public class SupplyController {

    private final SupplyService supplyService;

    @GetMapping
    public ResponseEntity<List<Supply>> findAll() {
        return ResponseEntity.ok(supplyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supply> findById(@PathVariable String id) {
        return ResponseEntity.ok(supplyService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Supply> create(@RequestBody Supply supply) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplyService.create(supply));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Supply> update(@PathVariable String id, @RequestBody Supply supply) {
        return ResponseEntity.ok(supplyService.update(id, supply));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        supplyService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Supply> updateStock(@PathVariable String id,
                                              @Valid @RequestBody StockUpdateRequest request) {
        return ResponseEntity.ok(supplyService.updateStock(id, request.getQuantity()));
    }
}
