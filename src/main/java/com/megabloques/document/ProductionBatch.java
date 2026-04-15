package com.megabloques.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "production_batches")
public class ProductionBatch {

    @Id
    private String id;
    private String productId;
    private String productName;
    private Instant date;
    private int cementBags;
    private int unitsProduced;
    private double kgPerBag;
    private double totalCementKg;
    private double productionCost;
    private double unitCost;
    private String qualityStatus;
    private String notes;
    @CreatedDate
    private Instant createdAt;
}
