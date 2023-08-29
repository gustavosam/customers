package com.microservice.customer.service;

import com.microservice.customer.documents.Customers;
import com.microservice.customer.model.CustomerConsult;
import com.microservice.customer.model.CustomerCreate;
import com.microservice.customer.model.CustomerUpdate;

public interface CustomerService {

    CustomerConsult createCustomer(CustomerCreate customers);

    Boolean existCustomer(String customerDocument);

    CustomerConsult getCustomerById(String customerDocument);

    String unsubscribeCustomer(String customerDocument);

    CustomerConsult updateCustomer(String customerDocument, CustomerUpdate customer);
}
