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
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    private String id;
    private String name;
    private String description;
    private String type;
    private double heightCm;
    private double lengthCm;
    private double widthCm;
    private int unitsPerBatch;
    @Builder.Default
    private List<RecipeItem> recipe = new ArrayList<>();
    @Builder.Default
    private double productionCost = 0;
    @Builder.Default
    private double unitCost = 0;
    @Builder.Default
    private int stock = 0;
    @Builder.Default
    private boolean active = true;
    @CreatedDate
    private Instant createdAt;
    @LastModifiedDate
    private Instant updatedAt;
}
