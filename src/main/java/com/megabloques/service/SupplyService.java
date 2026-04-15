package com.megabloques.service;

import com.megabloques.document.Product;
import com.megabloques.document.RecipeItem;
import com.megabloques.document.Supply;
import com.megabloques.exception.ResourceNotFoundException;
import com.megabloques.repository.ProductRepository;
import com.megabloques.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final ProductRepository productRepository;

    public List<Supply> findAll() {
        return supplyRepository.findAll();
    }

    public Supply findById(String id) {
        return supplyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supply not found with id: " + id));
    }

    public Supply create(Supply supply) {
        supply.setId(null);
        return supplyRepository.save(supply);
    }

    public Supply update(String id, Supply supply) {
        Supply existing = findById(id);
        existing.setName(supply.getName());
        existing.setDescription(supply.getDescription());
        existing.setCategory(supply.getCategory());
        existing.setPurchaseUnit(supply.getPurchaseUnit());
        existing.setRecipeUnit(supply.getRecipeUnit());
        existing.setConversionFactor(supply.getConversionFactor());
        existing.setUnitCost(supply.getUnitCost());
        existing.setRecipeCost(supply.getRecipeCost());
        existing.setStock(supply.getStock());
        existing.setActive(supply.isActive());

        Supply saved = supplyRepository.save(existing);
        propagateSupplyChangesToProducts(saved);
        return saved;
    }

    public void delete(String id) {
        Supply supply = findById(id);
        supplyRepository.delete(supply);
    }

    public Supply updateStock(String id, double quantity) {
        Supply supply = findById(id);
        supply.setStock(quantity);
        return supplyRepository.save(supply);
    }

    private void propagateSupplyChangesToProducts(Supply updatedSupply) {
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            boolean changed = false;
            for (RecipeItem item : product.getRecipe()) {
                if (item.getSupply() != null && updatedSupply.getId().equals(item.getSupply().getId())) {
                    item.setSupply(updatedSupply);
                    item.setTotalCost(item.getQuantity() * updatedSupply.getRecipeCost());
                    changed = true;
                }
            }
            if (changed) {
                recalculateProductCosts(product);
                productRepository.save(product);
            }
        }
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
