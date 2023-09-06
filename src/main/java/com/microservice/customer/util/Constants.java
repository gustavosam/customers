package com.microservice.customer.util;

/**
 * Esta clase define las constantes usadas en el micro servicio customer.
 * */
public class Constants {

  public static final String DOCUMENT_EMPTY = "El documento del cliente no puede estar vacío";

  public static final String EMAIL_EMPTY = "El email del cliente no puede estar vacío";

  public static final String NAME_EMPTY = "El nombre del cliente no puede estar vacío";

  public static final String CLIENT_TYPE_EMPTY = "El tipo de cliente no puede estar vacío";

  public static final String CLIENT_EXIST = "El cliente ya se encuentra registrado";

  public static final String CANT_UPDATE_PYME = "Usted debe ser un cliente Company para ser Pyme";

  public static final String CANT_UPDATE_VIP = "Usted debe ser un cliente personal para ser Vip";

  public static final String NOT_ORDINARY_ACCOUNT = "Debes tener una cuenta corriente";

  public static final String NOT_SAVING_ACCOUNT = "No tienes cuentas de ahorro con $500";

  public static final String NOT_CREDIT_CARD = "No posees una tarjeta de crédito";

  public static final String CLIENT_NOT_EXIST = "El documento no existe en el sistema";

  public static final String CLIENT_CREATED = "El cliente fue creado con éxito";

  public static final String CLIENT_GET = "Se obtuvo la información del cliente con éxito";

  public static final String CLIENT_UNSUBSCRIBE = "EL cliente fue dado de bajo con éxito";

  public static final String CLIENT_UPDATED = "EL cliente fue actualizado con éxito";
}
