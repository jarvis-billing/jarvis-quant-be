package com.megabloques.service;

import com.megabloques.document.Product;
import com.megabloques.document.ProductionBatch;
import com.megabloques.dto.RegisterProductionRequest;
import com.megabloques.exception.ResourceNotFoundException;
import com.megabloques.repository.ProductRepository;
import com.megabloques.repository.ProductionBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionBatchService {

    private final ProductionBatchRepository productionBatchRepository;
    private final ProductRepository productRepository;

    public List<ProductionBatch> findAll(String productId) {
        if (productId != null && !productId.isBlank()) {
            return productionBatchRepository.findByProductIdOrderByCreatedAtDesc(productId);
        }
        return productionBatchRepository.findAllByOrderByCreatedAtDesc();
    }

    public ProductionBatch register(RegisterProductionRequest request) {
        int unitsProduced = request.getCementBags() * request.getUnitsPerBag();
        double totalCementKg = request.getCementBags() * request.getKgPerBag();
        double productionCost = request.getCementBags() * request.getProductionCostPerBatch();
        double unitCost = productionCost / unitsProduced;

        ProductionBatch batch = ProductionBatch.builder()
                .productId(request.getProductId())
                .productName(request.getProductName())
                .date(Instant.now())
                .cementBags(request.getCementBags())
                .unitsProduced(unitsProduced)
                .kgPerBag(request.getKgPerBag())
                .totalCementKg(totalCementKg)
                .productionCost(productionCost)
                .unitCost(unitCost)
                .qualityStatus("PENDING")
                .notes(request.getNotes())
                .build();

        ProductionBatch saved = productionBatchRepository.save(batch);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + request.getProductId()));
        product.setStock(product.getStock() + unitsProduced);
        productRepository.save(product);

        return saved;
    }

    public ProductionBatch updateQualityStatus(String id, String status) {
        ProductionBatch batch = productionBatchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionBatch not found with id: " + id));
        batch.setQualityStatus(status);
        return productionBatchRepository.save(batch);
    }

    public void delete(String id) {
        ProductionBatch batch = productionBatchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionBatch not found with id: " + id));

        Product product = productRepository.findById(batch.getProductId()).orElse(null);
        if (product != null) {
            product.setStock(Math.max(0, product.getStock() - batch.getUnitsProduced()));
            productRepository.save(product);
        }

        productionBatchRepository.delete(batch);
    }
}
