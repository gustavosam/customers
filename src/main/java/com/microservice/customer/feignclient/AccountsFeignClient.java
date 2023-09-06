package com.microservice.customer.feignclient;

import com.microservice.customer.util.AccountDto;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Esta interfaz es la encargada de comunicarse con el microservicio de cuentas.
 * */
@FeignClient(name = "ms-accounts", url = "localhost:8083")
public interface AccountsFeignClient {

  /**
   * Este método permite obtener la información de las cuentas.
   * */
  @GetMapping("/account/client/{document}")
  List<AccountDto> getAccountsByClient(@PathVariable String document);
}
