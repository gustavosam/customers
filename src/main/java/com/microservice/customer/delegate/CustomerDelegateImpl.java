package com.microservice.customer.delegate;

import com.microservice.customer.model.CustomerUpdate;
import com.microservice.customer.util.ClassError;
import com.microservice.customer.api.CustomerApiDelegate;
import com.microservice.customer.model.CustomerConsult;
import com.microservice.customer.model.CustomerCreate;
import com.microservice.customer.service.CustomerService;
import com.microservice.customer.util.Constants;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassError.getInstance(Constants.MESSAGE_ERROR_DOCUMENT_EMPTY));
        }

        if(customerCreate.getCustomerEmail() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassError.getInstance(Constants.MESSAGE_ERROR_EMAIL_EMPTY));
        }

        if(customerCreate.getCustomerName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassError.getInstance(Constants.MESSAGE_ERROR_NAME_EMPTY));
        }

        if(customerCreate.getCustomerType() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassError.getInstance(Constants.MESSAGE_ERROR_CUSTOMER_TYPE_EMPTY));
        }

        return customerService.existCustomer(customerCreate.getCustomerDocument())
                ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassError.getInstance(Constants.MESSAGE_ERROR_CUSTOMER_EXIST))
                : ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customerCreate));

    }

    @Override
    public ResponseEntity<CustomerConsult> getCustomer(String customerDocument){

        return customerService.existCustomer(customerDocument)
                ? ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomerById(customerDocument))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(ClassError.getInstance(Constants.MESSAGE_ERROR_CUSTOMER_NOT_EXIST));
    }


    @Override
    public ResponseEntity<String> unsubscribeCustomer(String customerDocument){

        return customerService.existCustomer(customerDocument)
                ? ResponseEntity.status(HttpStatus.OK).body(customerService.unsubscribeCustomer(customerDocument))
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(Constants.MESSAGE_ERROR_CUSTOMER_NOT_EXIST);
    }

    @Override
    public ResponseEntity<CustomerConsult> updateCustomer(String customerDocument, CustomerUpdate customerUpdate){

        if(!customerService.existCustomer(customerDocument)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ClassError.getInstance(Constants.MESSAGE_ERROR_CUSTOMER_NOT_EXIST));
        }

        if(customerUpdate.getCustomerEmail() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassError.getInstance(Constants.MESSAGE_ERROR_EMAIL_EMPTY));
        }

        if(customerUpdate.getCustomerName() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassError.getInstance(Constants.MESSAGE_ERROR_NAME_EMPTY));
        }

        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(customerDocument, customerUpdate));
    }
}
