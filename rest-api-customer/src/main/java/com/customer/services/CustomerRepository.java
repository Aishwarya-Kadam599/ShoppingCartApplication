package com.customer.services;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.customer.entities.Customer;


public interface CustomerRepository extends CrudRepository<Customer,Integer>
{
    @Query("select u from Customer u")
    public List<Customer> getAllCustomers();

}
