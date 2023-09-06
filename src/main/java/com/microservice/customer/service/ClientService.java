package com.microservice.customer.service;

import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.model.ClientUpdate;
import com.microservice.customer.util.ClientDto;

/**
 * Interfaz en la que se define métodos que serán implementados por ClientServiceImpl
 * Y contienen la lógica de negocio para el registro, actualización y obtención del cliente.
 * */
public interface ClientService {

  ClientDto createClient(ClientCreate client);

  Boolean existClient(String document);

  ClientDto getClientById(String document);

  String unsubscribeClient(String document);

  ClientDto updateClient(String document, ClientUpdate client);

  ClientDto updatePyme(String document);

  ClientDto updateVip(String document);

  Boolean haveOrdinaryAccount(String document);

  Boolean haveSavingAccount(String document);

  Boolean haveCreditCard(String document);
}
