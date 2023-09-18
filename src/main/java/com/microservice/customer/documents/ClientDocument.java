package com.microservice.customer.documents;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Esta clase representa la colección clients en la base de datos mongo.
 */
@Getter
@Setter
@Document(collection = "clients")
public class ClientDocument {

  @Id
  @NotNull
  private String document;

  @NotNull
  private String name;

  @NotNull
  private String clientType;

  @NotNull
  private String email;

  @NotNull
  private Boolean isActive;

  private Boolean expiredDebt;

  private LocalDate clientCreationDate;
}
