package com.microservice.customer.util;

import com.microservice.customer.model.CustomerConsult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClaseError extends CustomerConsult {

    private  String mensajeError;
    private static ClaseError instance;



    private ClaseError(){

    }

    public static ClaseError getInstance(String mensaje){
        if(instance == null){
            instance = new ClaseError();
        }
        instance.mensajeError=mensaje;
        return instance;
    }
}
