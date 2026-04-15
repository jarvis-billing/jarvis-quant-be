package com.megabloques.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleItem {

    private String itemType;
    private String itemId;
    private String itemName;
    private String unit;
    private double quantity;
    private double unitPrice;
    private double subtotal;
}
