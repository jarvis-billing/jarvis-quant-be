package com.megabloques.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sales")
public class Sale {

    @Id
    private String id;
    private Instant date;
    private String clientId;
    private String clientName;
    @Builder.Default
    private List<SaleItem> items = new ArrayList<>();
    @Builder.Default
    private double itemsSubtotal = 0;
    @Builder.Default
    private double transportCost = 0;
    @Builder.Default
    private double total = 0;
    private String notes;
    @CreatedDate
    private Instant createdAt;
}
