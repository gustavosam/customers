package com.microservice.customer.util;

import com.microservice.customer.model.CustomerConsult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassError extends CustomerConsult {

    private  String mensajeError;
    private static ClassError instance;

    private ClassError(){

    }

    public static ClassError getInstance(String mensaje){
        if(instance == null){
            instance = new ClassError();
        }
        instance.mensajeError=mensaje;
        return instance;
    }
}
