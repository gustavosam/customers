package com.microservice.customer.service;

import com.microservice.customer.documents.Customers;
import com.microservice.customer.model.CustomerConsult;
import com.microservice.customer.model.CustomerCreate;
import com.microservice.customer.model.CustomerUpdate;
import com.microservice.customer.repository.CustomersRepository;
import com.microservice.customer.service.mapper.CustomersMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomersRepository customersRepository;

    @Override
    public CustomerConsult createCustomer(CustomerCreate customers) {
        return CustomersMappers.mapCustomerToCustomerConsult(
                customersRepository.save(CustomersMappers.mapCustomerCreateToCustomer(customers))
        );
    }

    @Override
    public Boolean existCustomer(String customerDocument) {
        return customersRepository.existsById(customerDocument);
    }

    @Override
    public CustomerConsult getCustomerById(String customerDocument) {

        if(! customersRepository.existsById(customerDocument)){
            return new CustomerConsult();
        }
        return CustomersMappers.mapCustomerToCustomerConsult( customersRepository.findById(customerDocument).get());
    }

    @Override
    public String unsubscribeCustomer(String customerDocument) {
        Customers customers = customersRepository.findById(customerDocument).get();
        customers.setIsActive(false);

        customersRepository.save(customers);
        return "El cliente fue dado de baja";
    }

    @Override
    public CustomerConsult updateCustomer(String customerDocument, CustomerUpdate customerUpdate) {

        Customers customers = customersRepository.findById(customerDocument).get();
        customers.setCustomerName(customerUpdate.getCustomerName());
        customers.setCustomerEmail(customerUpdate.getCustomerEmail());

        return CustomersMappers.mapCustomerToCustomerConsult( customersRepository.save(customers) );
    }
}
