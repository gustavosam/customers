package com.microservice.customer.delegate;

import com.microservice.customer.api.ClientApiDelegate;
import com.microservice.customer.model.ClientConsult;
import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.model.ClientUpdate;
import com.microservice.customer.service.ClientService;
import com.microservice.customer.util.Constants;
import com.microservice.customer.util.ErrorC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Esta clase implementa a la interfaz delegate generada por open api.
 */
@Service
public class ClientDelegateImpl implements ClientApiDelegate {

  @Autowired
  private ClientService clientService;

  @Override
  public ResponseEntity<ClientConsult> createClient(ClientCreate clientCreate) {

    if (clientCreate.getDocument() == null) {
      return ResponseEntity.badRequest().body(ErrorC.getObj(Constants.DOCUMENT_EMPTY));
    }

    if (clientCreate.getEmail() == null) {
      return ResponseEntity.badRequest().body(ErrorC.getObj(Constants.EMAIL_EMPTY));
    }

    if (clientCreate.getName() == null) {
      return ResponseEntity.badRequest().body(ErrorC.getObj(Constants.NAME_EMPTY));
    }

    if (clientCreate.getClientType() == null) {
      return ResponseEntity.badRequest().body(ErrorC.getObj(Constants.CLIENT_TYPE_EMPTY));
    }

    return clientService.existClient(clientCreate.getDocument())
      ? ResponseEntity.badRequest().body(ErrorC.getObj(Constants.CLIENT_EXIST))
      : ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(clientCreate));
  }

  @Override
  public ResponseEntity<ClientConsult> getClient(String document) {

    return clientService.existClient(document)
      ? ResponseEntity.ok(clientService.getClientById(document))
      : new ResponseEntity<>(ErrorC.getObj(Constants.CLIENT_NOT_EXIST), HttpStatus.NOT_FOUND);
  }


  @Override
  public ResponseEntity<String> unsubscribeClient(String document) {

    return clientService.existClient(document)
      ? ResponseEntity.ok(clientService.unsubscribeClient(document))
      : new ResponseEntity<>(Constants.CLIENT_NOT_EXIST, HttpStatus.NOT_FOUND);
  }

  @Override
  public ResponseEntity<ClientConsult> updateClient(String document, ClientUpdate client) {

    if (!clientService.existClient(document)) {
      return new ResponseEntity<>(ErrorC.getObj(Constants.CLIENT_NOT_EXIST), HttpStatus.NOT_FOUND);
    }

    if (client.getEmail() == null) {
      return ResponseEntity.badRequest().body(ErrorC.getObj(Constants.EMAIL_EMPTY));
    }

    if (client.getName() == null) {
      return ResponseEntity.badRequest().body(ErrorC.getObj(Constants.NAME_EMPTY));
    }

    return ResponseEntity.ok(clientService.updateClient(document, client));
  }
}
