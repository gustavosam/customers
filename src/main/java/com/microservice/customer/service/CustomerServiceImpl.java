package com.microservice.customer.service;

import com.microservice.customer.documents.CustomersDocuments;
import com.microservice.customer.model.CustomerCreate;
import com.microservice.customer.model.CustomerUpdate;
import com.microservice.customer.repository.CustomersRepository;
import com.microservice.customer.service.mapper.CustomersMappers;
import com.microservice.customer.util.Constants;
import com.microservice.customer.util.CustomerComplementary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomersRepository customersRepository;

    @Override
    public CustomerComplementary createCustomer(CustomerCreate customers) {

        CustomersDocuments customersDocuments = CustomersMappers.mapCustomerCreateToCustomerDocuments(customers);
        customersDocuments.setIsActive(true);
        customersDocuments.setCustomerCreationDate(LocalDate.now());

        CustomerComplementary customerCreated = CustomersMappers.mapCustomerDocumentsToCustomerComplementary(customersRepository.save(customersDocuments));
        customerCreated.setMessage(Constants.MESSAGE_CUSTOMER_CREATED);

        return customerCreated;
    }

    @Override
    public Boolean existCustomer(String customerDocument) {
        return customersRepository.existsById(customerDocument);
    }

    @Override
    public CustomerComplementary getCustomerById(String customerDocument) {

        CustomerComplementary customer = CustomersMappers.mapCustomerDocumentsToCustomerComplementary( customersRepository.findById(customerDocument).orElse(new CustomersDocuments()) );
        customer.setMessage(Constants.MESSAGE_CUSTOMER_GET);

        return customer;
    }

    @Override
    public String unsubscribeCustomer(String customerDocument) {
        CustomersDocuments customersDocuments = customersRepository.findById(customerDocument).orElse(new CustomersDocuments());
        customersDocuments.setIsActive(false);

        customersRepository.save(customersDocuments);

        return Constants.MESSAGE_CUSTOMER_UNSUBSCRIBE;
    }

    @Override
    public CustomerComplementary updateCustomer(String customerDocument, CustomerUpdate customerUpdate) {

        CustomersDocuments customersDocuments = customersRepository.findById(customerDocument).orElse(new CustomersDocuments());
        customersDocuments.setCustomerName(customerUpdate.getCustomerName());
        customersDocuments.setCustomerEmail(customerUpdate.getCustomerEmail());

        CustomerComplementary updatedCustomer = CustomersMappers.mapCustomerDocumentsToCustomerComplementary( customersRepository.save(customersDocuments) );
        updatedCustomer.setMessage(Constants.MESSAGE_CUSTOMER_UPDATED);

        return updatedCustomer;
    }
}
