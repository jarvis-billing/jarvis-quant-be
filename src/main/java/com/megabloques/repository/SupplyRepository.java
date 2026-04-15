package com.megabloques.repository;

import com.megabloques.document.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyRepository extends MongoRepository<Supply, String> {
}
