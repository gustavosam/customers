package com.microservice.customer.service.mapper;

import com.microservice.customer.documents.CustomersDocuments;
import com.microservice.customer.model.CustomerCreate;
import com.microservice.customer.util.CustomerComplementary;
import org.springframework.stereotype.Component;

@Component
public class CustomersMappers {

    public static CustomerComplementary mapCustomerDocumentsToCustomerComplementary(CustomersDocuments customersDocuments){
        CustomerComplementary customer = new CustomerComplementary();

        customer.setCustomerDocument(customersDocuments.getCustomerDocument());
        customer.setCustomerName(customersDocuments.getCustomerName());
        customer.setCustomerEmail(customersDocuments.getCustomerEmail());
        customer.setCustomerType(customersDocuments.getCustomerType());
        customer.setIsActive(customersDocuments.getIsActive());
        customer.setCustomerCreationDate(customersDocuments.getCustomerCreationDate());

        return customer;
    }

    public static CustomersDocuments mapCustomerCreateToCustomerDocuments(CustomerCreate customerCreate){
        CustomersDocuments customersDocuments = new CustomersDocuments();

        customersDocuments.setCustomerDocument(customerCreate.getCustomerDocument());
        customersDocuments.setCustomerEmail(customerCreate.getCustomerEmail());
        customersDocuments.setCustomerName(customerCreate.getCustomerName());
        customersDocuments.setCustomerType(customerCreate.getCustomerType().getValue());

        return customersDocuments;
    }
}
