package com.microservice.customer.service;

import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.model.ClientUpdate;
import com.microservice.customer.model.Products;
import com.microservice.customer.util.ClientDto;
import reactor.core.publisher.Mono;

/**
 * Interfaz en la que se define métodos que serán implementados por ClientServiceImpl
 * Y contienen la lógica de negocio para el registro, actualización y obtención del cliente.
 * */
public interface ClientService {

  Mono<ClientDto> createClient(ClientCreate client);

  Mono<Boolean> existClient(String document);

  Mono<ClientDto> getClientById(String document);

  String unsubscribeClient(String document);

  Mono<ClientDto> updateClient(String document, ClientUpdate client);

  Mono<ClientDto> updatePyme(String document);

  Mono<ClientDto> updateVip(String document);

  Mono<Boolean> haveOrdinaryAccount(String document);

  Mono<Boolean> haveSavingAccount(String document);

  Mono<Boolean> haveCreditCard(String document);

  Mono<Products> getProducts(String document);
}
