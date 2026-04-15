package com.megabloques.service;

import com.megabloques.document.Product;
import com.megabloques.document.RecipeItem;
import com.megabloques.document.Supply;
import com.megabloques.exception.ResourceNotFoundException;
import com.megabloques.repository.ProductRepository;
import com.megabloques.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplyRepository supplyRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product create(Product product) {
        product.setId(null);
        product.setRecipe(new ArrayList<>());
        product.setProductionCost(0);
        product.setUnitCost(0);
        return productRepository.save(product);
    }

    public Product update(String id, Product product) {
        Product existing = findById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setType(product.getType());
        existing.setHeightCm(product.getHeightCm());
        existing.setLengthCm(product.getLengthCm());
        existing.setWidthCm(product.getWidthCm());
        existing.setUnitsPerBatch(product.getUnitsPerBatch());
        existing.setStock(product.getStock());
        existing.setActive(product.isActive());

        if (existing.getRecipe() != null && !existing.getRecipe().isEmpty()) {
            recalculateProductCosts(existing);
        }

        return productRepository.save(existing);
    }

    public void delete(String id) {
        Product product = findById(id);
        productRepository.delete(product);
    }

    public Product updateStock(String id, double quantity) {
        Product product = findById(id);
        product.setStock((int) quantity);
        return productRepository.save(product);
    }

    public Product saveRecipe(String id, List<RecipeItem> recipeItems) {
        Product product = findById(id);
        List<RecipeItem> resolvedRecipe = new ArrayList<>();

        for (RecipeItem item : recipeItems) {
            Supply supply;
            if (item.getSupply() != null && item.getSupply().getId() != null) {
                supply = supplyRepository.findById(item.getSupply().getId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Supply not found with id: " + item.getSupply().getId()));
            } else {
                throw new ResourceNotFoundException("Supply reference is required in each recipe item");
            }

            double totalCost = item.getQuantity() * supply.getRecipeCost();
            resolvedRecipe.add(RecipeItem.builder()
                    .supply(supply)
                    .quantity(item.getQuantity())
                    .totalCost(totalCost)
                    .build());
        }

        product.setRecipe(resolvedRecipe);
        recalculateProductCosts(product);
        return productRepository.save(product);
    }

    private void recalculateProductCosts(Product product) {
        double productionCost = product.getRecipe().stream()
                .mapToDouble(RecipeItem::getTotalCost)
                .sum();
        product.setProductionCost(productionCost);
        if (product.getUnitsPerBatch() > 0) {
            product.setUnitCost(productionCost / product.getUnitsPerBatch());
        }
    }
}
