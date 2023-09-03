package com.microservice.customer.service;

import com.microservice.customer.model.CustomerCreate;
import com.microservice.customer.model.CustomerUpdate;
import com.microservice.customer.util.CustomerComplementary;

public interface CustomerService {

    CustomerComplementary createCustomer(CustomerCreate customers);

    Boolean existCustomer(String customerDocument);

    CustomerComplementary getCustomerById(String customerDocument);

    String unsubscribeCustomer(String customerDocument);

    CustomerComplementary updateCustomer(String customerDocument, CustomerUpdate customer);
}
