package com.microservice.customer.service;

import com.microservice.customer.documents.ClientDocument;
import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.model.ClientUpdate;
import com.microservice.customer.repository.ClientRepository;
import com.microservice.customer.service.mapper.MapstructMapper;
import com.microservice.customer.util.CardDto;
import com.microservice.customer.util.ClientDto;
import com.microservice.customer.util.Constants;
import java.time.LocalDate;
import com.microservice.customer.webclient.AccountsWebClient;
import com.microservice.customer.webclient.CreditCardWebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * La clase ClientServiceImpl contiene la lógica de negocio para la creación
 * actualización, dar de baja y obtener información sobre un cliente.
 * */
@Service
public class ClientServiceImpl implements ClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private AccountsWebClient accountsWebClient;

  @Autowired
  private CreditCardWebClient creditCardWebClient;

  @Autowired
  private MapstructMapper mapper;

  @Override
  public Mono<ClientDto> createClient(ClientCreate client) {


    ClientDocument clientDocument = mapper.clientCreateToClientDocument(client);
    clientDocument.setIsActive(true);
    clientDocument.setClientCreationDate(LocalDate.now());

    Mono<ClientDocument> clientDocumentMonoCreated = clientRepository.save(clientDocument);

    Mono<ClientDto> clientDtoMono = clientDocumentMonoCreated.map(clientCreated -> mapper.clientDocToClientDto(clientCreated));

    return clientDtoMono.map(clientDto -> {
      clientDto.setMessage(Constants.CLIENT_CREATED);
      return clientDto;
    });

  }

  @Override
  public Mono<Boolean> existClient(String document) {
    return clientRepository.existsById(document);
  }

  @Override
  public Mono<ClientDto> getClientById(String document) {

    Mono<ClientDocument> clientDocumentMono = clientRepository.findById(document);

    return clientDocumentMono.map(clientDocument -> {
      ClientDto clientDto = mapper.clientDocToClientDto(clientDocument);
      clientDto.setMessage(Constants.CLIENT_GET);
      return clientDto;
    });
  }

  @Override
  public String unsubscribeClient(String document) {

    Mono<ClientDocument> clientDocumentMono = clientRepository.findById(document);

    Mono<ClientDocument> clientDocumentMonoUnsubscribe = clientDocumentMono.flatMap(clientDocument -> {
      clientDocument.setIsActive(false);
      return clientRepository.save(clientDocument);
    });

    return Constants.CLIENT_UNSUBSCRIBE;
  }

  @Override
  public Mono<ClientDto> updateClient(String document, ClientUpdate clientUpdate) {

    Mono<ClientDocument> clientDocumentMono = clientRepository.findById(document);

    Mono<ClientDocument> clientDocumentMonoUpdated = clientDocumentMono.flatMap(clientDocument -> {
      clientDocument.setName(clientUpdate.getName());
      clientDocument.setEmail(clientUpdate.getEmail());

      return clientRepository.save(clientDocument);
    });

    Mono<ClientDto> clientDtoMono = clientDocumentMonoUpdated.map(clientDocument -> mapper.clientDocToClientDto(clientDocument));

    return clientDtoMono.map(clientDto -> {
      clientDto.setMessage(Constants.CLIENT_UPDATED);
      return clientDto;
    });

  }

  @Override
  public Mono<ClientDto> updatePyme(String document) {

    Mono<ClientDocument> clientDocumentMono = clientRepository.findById(document);

    Mono<ClientDocument> clientDocumentMonoUpdated = clientDocumentMono.flatMap(clientDocument -> {
      clientDocument.setClientType("PYME");
      return clientRepository.save(clientDocument);
    });

    Mono<ClientDto> clientDtoMono = clientDocumentMonoUpdated.map(clientDocument -> mapper.clientDocToClientDto(clientDocument));

    return clientDtoMono.map(clientDto -> {
      clientDto.setMessage(Constants.CLIENT_UPDATED);
      return clientDto;
    });
  }

  @Override
  public Mono<ClientDto> updateVip(String document) {

    Mono<ClientDocument> clientDocumentMono = clientRepository.findById(document);

    Mono<ClientDocument> clientDocumentMonoUpdated = clientDocumentMono.flatMap(clientDocument -> {
      clientDocument.setClientType("VIP");
      return clientRepository.save(clientDocument);
    });

    Mono<ClientDto> clientDtoMono = clientDocumentMonoUpdated.map(clientDocument -> mapper.clientDocToClientDto(clientDocument));

    return clientDtoMono.map(clientDto -> {
      clientDto.setMessage(Constants.CLIENT_UPDATED);
      return clientDto;
    });

  }

  @Override
  public Mono<Boolean> haveOrdinaryAccount(String document) {
    return accountsWebClient.getAccountsByClient(document)
            .any(account -> account.getAccountType().equalsIgnoreCase("CORRIENTE"));

  }

  @Override
  public Mono<Boolean> haveSavingAccount(String document) {
    return accountsWebClient.getAccountsByClient(document)
            .any(account -> account.getAccountType().equalsIgnoreCase("AHORRO")
                    && account.getAccountAmount() >= 500);

  }

  @Override
  public Mono<Boolean> haveCreditCard(String document) {
    Flux<CardDto> cardDtos = creditCardWebClient.getCreditCards(document);

    return cardDtos
            .count()
            .map(count -> count > 0);
  }
}
