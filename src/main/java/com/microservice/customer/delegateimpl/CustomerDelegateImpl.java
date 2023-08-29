package com.microservice.customer.delegateimpl;

import com.microservice.customer.model.CustomerUpdate;
import com.microservice.customer.util.ClaseError;
import com.microservice.customer.api.CustomerApiDelegate;
import com.microservice.customer.model.CustomerConsult;
import com.microservice.customer.model.CustomerCreate;
import com.microservice.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerDelegateImpl implements CustomerApiDelegate {

    @Autowired
    private CustomerService customerService;

    @Override
    public ResponseEntity<CustomerConsult> createCustomer(CustomerCreate customerCreate){

        if(customerCreate.getCustomerDocument() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClaseError.getInstance("El documento del cliente no puede ser nulo"));
        }

        if(customerCreate.getCustomerEmail() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClaseError.getInstance("El email del cliente no puede ser nulo"));
        }

        if(customerCreate.getCustomerName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClaseError.getInstance("El nombre del cliente no puede ser nulo"));
        }

        if(customerCreate.getCustomerType() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClaseError.getInstance("El tipo de cliente no puede ser nulo"));
        }

        return customerService.existCustomer(customerCreate.getCustomerDocument()) ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClaseError.getInstance("El cliente ya se encuentra registrado")) :  ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerCreate));

    }

    @Override
    public ResponseEntity<CustomerConsult> getCustomer(String customerDocument){

        if(customerDocument.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClaseError.getInstance("El documento es invalido"));
        }

        return customerService.existCustomer(customerDocument) ? ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(customerDocument)) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClaseError.getInstance("El documento no existe en el sistema"));
    }


    @Override
    public ResponseEntity<String> unsubscribeCustomer(String customerDocument){

        return customerService.existCustomer(customerDocument)
                ? ResponseEntity.status(HttpStatus.OK).body(customerService.unsubscribeCustomer(customerDocument))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("El documente no existe");

    }


    @Override
    public ResponseEntity<CustomerConsult> updateCustomer(String customerDocument, CustomerUpdate customerUpdate){

        if(!customerService.existCustomer(customerDocument)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ClaseError.getInstance("El cliente no existe"));
        }

        if(customerUpdate.getCustomerEmail() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClaseError.getInstance("El email del cliente no puede ser nulo"));
        }

        if(customerUpdate.getCustomerName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClaseError.getInstance("El nombre del cliente no puede ser nulo"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(customerDocument, customerUpdate));

    }
}
