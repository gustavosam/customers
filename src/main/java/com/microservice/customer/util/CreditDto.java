package com.microservice.customer.util;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditDto {

  private String creditNumber;

  private String creditType;

  private Double creditAmount;

  private Double pendingPay;

  private Double amountPaid;

  private String clientDocument;

  private LocalDate creditDate;
}
