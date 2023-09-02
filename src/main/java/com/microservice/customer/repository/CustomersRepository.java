package com.microservice.customer.repository;

import com.microservice.customer.documents.CustomersDocuments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomersRepository extends MongoRepository<CustomersDocuments, String> {
}
