package com.microservice.customer.util;

import com.microservice.customer.model.CustomerConsult;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerComplementary extends CustomerConsult {

    private String customerDocument;

    private String customerName;

    private String customerType;

    private String customerEmail;

    private Boolean isActive;

    private LocalDate customerCreationDate;
}
