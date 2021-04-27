package com.customer.services;

import java.util.List;

import com.customer.entities.CustomerLogin;
import com.customer.entities.CustomerProfile;
import com.customer.exceptions.IncorrectPaswordException;
import com.customer.exceptions.UnableToStoreException;

public interface CustomerService {
	CustomerProfile createCustomer(CustomerProfile customer) throws NullPointerException, UnableToStoreException;
	List<CustomerProfile> getCustomers();
	void deleteCustomer(Integer id);
	CustomerProfile getCustomerById(Integer id);
	CustomerProfile LoginCustomer(String email, String password) throws NullPointerException, IncorrectPaswordException;
	CustomerLogin getCustomerByemail(String email);
}
