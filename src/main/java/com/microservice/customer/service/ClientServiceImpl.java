package com.microservice.customer.service;

import com.microservice.customer.documents.ClientDocument;
import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.model.ClientUpdate;
import com.microservice.customer.repository.ClientRepository;
import com.microservice.customer.service.mapper.Mappers;
import com.microservice.customer.util.ClientDto;
import com.microservice.customer.util.Constants;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * La clase ClientServiceImpl contiene la lógica de negocio para la creación
 * actualización, dar de baja y obtener información sobre un cliente.
 * */
@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public ClientDto createClient(ClientCreate client) {

    ClientDocument clientDoc = Mappers.clientCreateToClientDocument(client);
    clientDoc.setIsActive(true);
    clientDoc.setClientCreationDate(LocalDate.now());

    ClientDto clientDto = Mappers.clientDocToClientDto(clientRepository.save(clientDoc));
    clientDto.setMessage(Constants.CLIENT_CREATED);

    return clientDto;
  }

  @Override
  public Boolean existClient(String document) {
    return clientRepository.existsById(document);
  }

  @Override
  public ClientDto getClientById(String document) {

    ClientDto clientDto = Mappers.clientDocToClientDto(clientRepository.findById(document)
                    .orElse(new ClientDocument()));

    clientDto.setMessage(Constants.CLIENT_GET);

    return clientDto;
  }

  @Override
  public String unsubscribeClient(String document) {

    ClientDocument clientDoc = clientRepository.findById(document)
            .orElse(new ClientDocument());

    clientDoc.setIsActive(false);

    clientRepository.save(clientDoc);

    return Constants.CLIENT_UNSUBSCRIBE;
  }

  @Override
  public ClientDto updateClient(String document, ClientUpdate clientUpdate) {

    ClientDocument clientDocument = clientRepository.findById(document)
            .orElse(new ClientDocument());

    clientDocument.setName(clientUpdate.getName());
    clientDocument.setEmail(clientUpdate.getEmail());

    ClientDto updatedClient = Mappers.clientDocToClientDto(clientRepository.save(clientDocument));
    updatedClient.setMessage(Constants.CLIENT_UPDATED);

    return updatedClient;
  }
}
