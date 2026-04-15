package com.megabloques.service;

import com.megabloques.document.Sale;
import com.megabloques.document.SaleItem;
import com.megabloques.exception.ResourceNotFoundException;
import com.megabloques.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;

    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    public Sale findById(String id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
    }

    public Sale create(Sale sale) {
        sale.setId(null);
        if (sale.getDate() == null) {
            sale.setDate(Instant.now());
        }

        double itemsSubtotal = 0;
        if (sale.getItems() != null) {
            for (SaleItem item : sale.getItems()) {
                item.setSubtotal(item.getQuantity() * item.getUnitPrice());
                itemsSubtotal += item.getSubtotal();
            }
        }

        sale.setItemsSubtotal(itemsSubtotal);
        sale.setTotal(itemsSubtotal + sale.getTransportCost());

        return saleRepository.save(sale);
    }

    public void delete(String id) {
        Sale sale = findById(id);
        saleRepository.delete(sale);
    }
}
