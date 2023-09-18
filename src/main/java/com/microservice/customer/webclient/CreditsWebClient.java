package com.microservice.customer.webclient;

import com.microservice.customer.util.AccountDto;
import com.microservice.customer.util.CreditDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class CreditsWebClient {

  @Autowired
  private WebClient.Builder webClientBuilder;

  @CircuitBreaker(name = "myCircuitBreaker", fallbackMethod = "fallbackMethod")
  public Flux<CreditDto> getCreditsByClient(String clientDocument){
    return webClientBuilder.build()
            .get()
            .uri("http://localhost:8081/credit/client/{clientDocument}", clientDocument)
            .retrieve()
            .bodyToFlux(CreditDto.class);
  }

  public Flux<CreditDto> fallbackMethod(String clientDocument, Exception ex) {

    return Flux.empty();
  }
}
