package com.megabloques.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "supplies")
public class Supply {

    @Id
    private String id;
    private String name;
    private String description;
    private String category;
    private String purchaseUnit;
    private String recipeUnit;
    private double conversionFactor;
    private double unitCost;
    private double recipeCost;
    @Builder.Default
    private double stock = 0;
    @Builder.Default
    private boolean active = true;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
