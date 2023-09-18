package com.microservice.customer.util;

import com.microservice.customer.model.ClientConsult;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase es un DTO que extiende del ClientConsult y añade nuevos atributos
 * para poder responder en las peticiones
 * que necesita el ClientDelegateImpl.
 * */
@Getter
@Setter
public class ClientDto extends ClientConsult {

  private String document;

  private String name;

  private String clientType;

  private String email;

  private Boolean isActive;

  private Boolean expiredDebt;

  private LocalDate clientCreationDate;
}
