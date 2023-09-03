package com.microservice.customer.service.mapper;

import com.microservice.customer.documents.ClientDocument;
import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.util.ClientDto;
import org.springframework.stereotype.Component;

/**
 * Esta clase define los mapers, para convertir una clase a otra.
 * */
@Component
public class Mappers {

  /**
   * Este método convierte una clase ClientDocument a una clase ClientDto.
   * */
  public static ClientDto clientDocToClientDto(ClientDocument clientDoc) {

    ClientDto clientDto = new ClientDto();
    clientDto.setDocument(clientDoc.getDocument());
    clientDto.setName(clientDoc.getName());
    clientDto.setEmail(clientDoc.getEmail());
    clientDto.setClientType(clientDoc.getClientType());
    clientDto.setIsActive(clientDoc.getIsActive());
    clientDto.setClientCreationDate(clientDoc.getClientCreationDate());

    return clientDto;
  }

  /**
   * Este método convierte una clase ClientCreate a una clase ClientDocument.
   * */
  public static ClientDocument clientCreateToClientDocument(ClientCreate clientCreate) {

    ClientDocument clientDocument = new ClientDocument();

    clientDocument.setDocument(clientCreate.getDocument());
    clientDocument.setEmail(clientCreate.getEmail());
    clientDocument.setName(clientCreate.getName());
    clientDocument.setClientType(clientCreate.getClientType().getValue());

    return clientDocument;
  }
}
