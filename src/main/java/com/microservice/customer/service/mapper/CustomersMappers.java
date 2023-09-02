package com.microservice.customer.service.mapper;

import com.microservice.customer.documents.CustomersDocuments;
import com.microservice.customer.model.CustomerConsult;
import com.microservice.customer.model.CustomerCreate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomersMappers {

    public static CustomerConsult mapCustomerToCustomerConsult(CustomersDocuments customersDocuments){
        CustomerConsult customerConsult = new CustomerConsult();
        customerConsult.setCustomerDocument(customersDocuments.getCustomerDocument());
        customerConsult.setCustomerName(customersDocuments.getCustomerName());
        customerConsult.setCustomerEmail(customersDocuments.getCustomerEmail());
        customerConsult.setCustomerType(customersDocuments.getCustomerType());
        customerConsult.setIsActive(customersDocuments.getIsActive());
        customerConsult.setCustomerCreationDate(customersDocuments.getCustomerCreationDate());

        return customerConsult;
    }

    public static CustomersDocuments mapCustomerCreateToCustomer(CustomerCreate customerCreate){
        CustomersDocuments customersDocuments = new CustomersDocuments();
        customersDocuments.setCustomerDocument(customerCreate.getCustomerDocument());
        customersDocuments.setCustomerEmail(customerCreate.getCustomerEmail());
        customersDocuments.setCustomerName(customerCreate.getCustomerName());
        customersDocuments.setCustomerType(customerCreate.getCustomerType().getValue());
        customersDocuments.setIsActive(true);
        customersDocuments.setCustomerCreationDate(LocalDate.now());
        return customersDocuments;
    }
}
