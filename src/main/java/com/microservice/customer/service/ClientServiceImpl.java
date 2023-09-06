package com.microservice.customer.service;

import com.microservice.customer.documents.ClientDocument;
import com.microservice.customer.feignclient.AccountsFeignClient;
import com.microservice.customer.feignclient.CreditCardFeignClient;
import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.model.ClientUpdate;
import com.microservice.customer.repository.ClientRepository;
import com.microservice.customer.service.mapper.MapstructMapper;
import com.microservice.customer.util.AccountDto;
import com.microservice.customer.util.CardDto;
import com.microservice.customer.util.ClientDto;
import com.microservice.customer.util.Constants;
import java.time.LocalDate;
import java.util.List;
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

  @Autowired
  private AccountsFeignClient accountsFeignClient;

  @Autowired
  private CreditCardFeignClient creditCardFeignClient;

  @Autowired
  private MapstructMapper mapper;

  @Override
  public ClientDto createClient(ClientCreate client) {

    ClientDocument clientDoc = mapper.clientCreateToClientDocument(client);
    clientDoc.setIsActive(true);
    clientDoc.setClientCreationDate(LocalDate.now());

    ClientDto clientDto = mapper.clientDocToClientDto(clientRepository.save(clientDoc));
    clientDto.setMessage(Constants.CLIENT_CREATED);

    return clientDto;
  }

  @Override
  public Boolean existClient(String document) {
    return clientRepository.existsById(document);
  }

  @Override
  public ClientDto getClientById(String document) {

    ClientDto clientDto = mapper.clientDocToClientDto(clientRepository.findById(document)
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

    ClientDto updatedClient = mapper.clientDocToClientDto(clientRepository.save(clientDocument));
    updatedClient.setMessage(Constants.CLIENT_UPDATED);

    return updatedClient;
  }

  @Override
  public ClientDto updatePyme(String document) {
    ClientDocument clientDocument = clientRepository.findById(document)
            .orElse(new ClientDocument());

    clientDocument.setClientType("PYME");

    ClientDto updatedClient = mapper.clientDocToClientDto(clientRepository.save(clientDocument));
    updatedClient.setMessage(Constants.CLIENT_UPDATED);

    return updatedClient;
  }

  @Override
  public ClientDto updateVip(String document) {
    ClientDocument clientDocument = clientRepository.findById(document)
            .orElse(new ClientDocument());

    clientDocument.setClientType("VIP");

    ClientDto updatedClient = mapper.clientDocToClientDto(clientRepository.save(clientDocument));
    updatedClient.setMessage(Constants.CLIENT_UPDATED);

    return updatedClient;
  }

  @Override
  public Boolean haveOrdinaryAccount(String document) {
    List<AccountDto> accountDtos = accountsFeignClient.getAccountsByClient(document);

    if (accountDtos.isEmpty()) {
      return false;
    }

    return accountDtos.stream()
            .anyMatch(account -> account.getAccountType().equalsIgnoreCase("CORRIENTE"));
  }

  @Override
  public Boolean haveSavingAccount(String document) {
    List<AccountDto> accountDtos = accountsFeignClient.getAccountsByClient(document);

    if (accountDtos.isEmpty()) {
      return false;
    }

    return accountDtos.stream()
            .anyMatch(account -> {
              return account.getAccountType().equalsIgnoreCase("AHORRO")
                      && account.getAccountAmount() >= 500;
            });
  }

  @Override
  public Boolean haveCreditCard(String document) {
    List<CardDto> cardDtos = creditCardFeignClient.getCreditCards(document);

    return !cardDtos.isEmpty();
  }
}
