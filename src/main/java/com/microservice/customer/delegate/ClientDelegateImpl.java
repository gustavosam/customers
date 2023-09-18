package com.microservice.customer.delegate;

import com.microservice.customer.api.ClientApiDelegate;
import com.microservice.customer.model.ClientConsult;
import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.model.ClientUpdate;
import com.microservice.customer.model.Products;
import com.microservice.customer.service.ClientService;
import com.microservice.customer.util.ClientDto;
import com.microservice.customer.util.Constants;
import com.microservice.customer.util.ErrorC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * Esta clase implementa a la interfaz delegate generada por open api.
 */
@Service
public class ClientDelegateImpl implements ClientApiDelegate {

  @Autowired
  private ClientService clientService;

  @Override
  public Mono<ResponseEntity<ClientConsult>> createClient(Mono<ClientCreate> clientCreate,
                                                   ServerWebExchange exchange)  {

    return clientCreate.flatMap(client -> {

      if (client.getDocument() == null) {
        return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.DOCUMENT_EMPTY)));
      }

      if (client.getEmail() == null) {
        return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.EMAIL_EMPTY)));
      }

      if (client.getName() == null) {
        return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.NAME_EMPTY)));
      }

      if (client.getClientType() == null) {
        return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.CLIENT_TYPE_EMPTY)));
      }

      Mono<Boolean> clientExist = clientService.existClient(client.getDocument());

      return clientExist.flatMap(clientExists -> {

        if (clientExists) {
          return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.CLIENT_EXIST)));
        }

        return clientService.createClient(client)
                  .map(clientConsult -> ResponseEntity.status(HttpStatus.CREATED).body(clientConsult));

      });
    });
  }

  @Override
  public Mono<ResponseEntity<ClientConsult>> getClient(String document,
                                                       ServerWebExchange exchange)  {

    return clientService.existClient(document)
            .flatMap(clientExist -> {

              if(clientExist){
                return clientService.getClientById(document).map(ResponseEntity::ok);
              }

              return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorC.getObj(Constants.CLIENT_NOT_EXIST)));
            });
  }

  @Override
  public Mono<ResponseEntity<ClientConsult>> updateClient(String document,
                                                          Mono<ClientUpdate> clientUpdate,
                                                          ServerWebExchange exchange) {

    return clientService.existClient(document)
            .flatMap(clientExist -> {

              if (!clientExist) {
                return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorC.getObj(Constants.CLIENT_NOT_EXIST)));
              }

              return clientUpdate.flatMap(updatedClient -> {

                if (updatedClient.getEmail() == null) {
                  return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.EMAIL_EMPTY)));
                }

                if (updatedClient.getName() == null) {
                  return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.NAME_EMPTY)));
                }

                return clientService.updateClient(document, updatedClient)
                        .map(ResponseEntity::ok);
              });
            });

  }

  @Override
  public Mono<ResponseEntity<ClientConsult>> updatePyme(String document,
                                                        ServerWebExchange exchange) {


    return clientService.existClient(document).flatMap(exist -> {
      if (!exist) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorC.getObj(Constants.CLIENT_NOT_EXIST)));
      }

      Mono<ClientDto> clientDtoMono = clientService.getClientById(document);

      return clientDtoMono.flatMap(clientDto -> {
        if (!clientDto.getClientType().equalsIgnoreCase("COMPANY")) {
          return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.CANT_UPDATE_PYME)));
        }

        Mono<Boolean> haveOrdinaryAccount = clientService.haveOrdinaryAccount(document);
        Mono<Boolean> haveCreditCard = clientService.haveCreditCard(document);

        return haveOrdinaryAccount.zipWith(haveCreditCard).flatMap(tuple -> {

          Boolean haveOrdinaryAcc = tuple.getT1();
          Boolean haveCard = tuple.getT2();

          if(!haveOrdinaryAcc){
            return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.NOT_ORDINARY_ACCOUNT)));
          }

          if(!haveCard){
            return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.NOT_CREDIT_CARD)));
          }
          return clientService.updatePyme(document)
                  .map(ResponseEntity::ok);

        });

      });
    });
  }

  @Override
  public Mono<ResponseEntity<ClientConsult>> updateVip(String document,
                                                       ServerWebExchange exchange) {

    return clientService.existClient(document).flatMap(exist -> {

      if(!exist) {
        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorC.getObj(Constants.CLIENT_NOT_EXIST)));
      }

      Mono<ClientDto> clientDtoMono = clientService.getClientById(document);

      return clientDtoMono.flatMap(clientDto -> {
        if(!clientDto.getClientType().equalsIgnoreCase("PERSONAL")){
          return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.CANT_UPDATE_VIP)));
        }

        Mono<Boolean> haveSavingAccount = clientService.haveSavingAccount(document);
        Mono<Boolean> haveCreditCard = clientService.haveCreditCard(document);

        return haveSavingAccount.zipWith(haveCreditCard).flatMap(tuple -> {

          Boolean haveSavingAcc = tuple.getT1();
          Boolean haveCard = tuple.getT2();

          if(!haveSavingAcc){
            return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.NOT_SAVING_ACCOUNT)));
          }

          if(!haveCard){
            return Mono.just(ResponseEntity.badRequest().body(ErrorC.getObj(Constants.NOT_CREDIT_CARD)));
          }

          return clientService.updateVip(document)
                  .map(ResponseEntity::ok);

        });
      });

    });
  }


  @Override
  public Mono<ResponseEntity<Products>> getProducts(String document,
                                                    ServerWebExchange exchange){

    return clientService.getProducts(document).map(ResponseEntity::ok);
  }
}
