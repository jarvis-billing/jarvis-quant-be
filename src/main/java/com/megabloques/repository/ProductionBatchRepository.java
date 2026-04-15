package com.megabloques.repository;

import com.megabloques.document.ProductionBatch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionBatchRepository extends MongoRepository<ProductionBatch, String> {

    List<ProductionBatch> findByProductIdOrderByCreatedAtDesc(String productId);

    List<ProductionBatch> findAllByOrderByCreatedAtDesc();
}
