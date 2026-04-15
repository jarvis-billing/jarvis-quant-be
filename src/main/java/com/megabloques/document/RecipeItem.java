package com.megabloques.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeItem {

    private Supply supply;
    private double quantity;
    private double totalCost;
}
