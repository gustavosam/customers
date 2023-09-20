package com.microservice.customer.webclient;

import com.microservice.customer.util.AccountDto;
import com.microservice.customer.util.CardDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class CreditCardWebClient {

  @Autowired
  private WebClient.Builder webClientBuilder;

  @CircuitBreaker(name = "circuitBreakerCreditCard", fallbackMethod = "fallbackMethod")
  public Flux<CardDto> getCreditCards(String document){
    return webClientBuilder.build()
            .get()
            .uri("http://localhost:8082/card/customer/{document}", document)
            .retrieve()
            .bodyToFlux(CardDto.class);
  }

  public Flux<CardDto> fallbackMethod(String document, Exception ex) {

    return Flux.empty();
  }
}
