package com.microservice.customer.util;

import com.microservice.customer.model.ClientConsult;
import lombok.Getter;
import lombok.Setter;

/**
 * Esta clase sirve para mostrar un mensaje de error al momento de que la petición
 * no cumpla con las validaciones necesarias del ClientDeletateIml y ClientServiceImpl.
 * */
@Getter
@Setter
public class ErrorC extends ClientConsult {

  private static ErrorC instance;

  private ErrorC() {

  }

  /**
   * Este método creará una única vez una instancia de esta clase
   * y colocará un mensaje.
   * */
  public static ErrorC getObj(String mensaje) {
    if (instance == null) {
      instance = new ErrorC();
    }
    instance.setMessage(mensaje);
    return instance;
  }
}
