package com.microservice.customer.service.mapper;

import com.microservice.customer.documents.Customers;
import com.microservice.customer.model.CustomerConsult;
import com.microservice.customer.model.CustomerCreate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomersMappers {

    public static CustomerConsult mapCustomerToCustomerConsult(Customers customers){
        CustomerConsult customerConsult = new CustomerConsult();
        customerConsult.setCustomerDocument(customers.getCustomerDocument());
        customerConsult.setCustomerName(customers.getCustomerName());
        customerConsult.setCustomerEmail(customers.getCustomerEmail());
        customerConsult.setCustomerType(customers.getCustomerType());
        customerConsult.setIsActive(customers.getIsActive());
        customerConsult.setCustomerCreationDate(customers.getCustomerCreationDate());

        return customerConsult;
    }

    public static Customers mapCustomerCreateToCustomer(CustomerCreate customerCreate){
        Customers customers = new Customers();
        customers.setCustomerDocument(customerCreate.getCustomerDocument());
        customers.setCustomerEmail(customerCreate.getCustomerEmail());
        customers.setCustomerName(customerCreate.getCustomerName());
        customers.setCustomerType(customerCreate.getCustomerType().getValue());
        customers.setIsActive(true);
        customers.setCustomerCreationDate(LocalDate.now());
        return customers;
    }
}
