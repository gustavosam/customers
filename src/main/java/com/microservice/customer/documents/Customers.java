package com.microservice.customer.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Getter
@Setter
@Document(collection = "customers")
public class Customers {

    @Id
    @NotNull
    private String customerDocument;

    @NotNull
    private String customerName;

    @NotNull
    private String customerType;

    @NotNull
    private String customerEmail;

    @NotNull
    private Boolean isActive;

    private LocalDate customerCreationDate;
}
