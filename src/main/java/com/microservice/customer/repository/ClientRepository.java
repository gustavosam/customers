package com.microservice.customer.repository;

import com.microservice.customer.documents.ClientDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Esta clase contiene los métodos necesario para el crud con mongo.
 * */
@Repository
public interface ClientRepository extends MongoRepository<ClientDocument, String> {
}
