package com.customer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.entities.CustomerLogin;
import com.customer.entities.CustomerProfile;
import com.customer.exceptions.IncorrectPaswordException;
import com.customer.exceptions.UnableToStoreException;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerLoginRepository customerLoginRepository;
	@Autowired
	private CustomerProfileRepository CustomerRepository;
	
	@Override
	public CustomerProfile createCustomer(CustomerProfile customer) throws NullPointerException, UnableToStoreException{
		CustomerProfile add = CustomerRepository.save(customer);
		if(add==null)
		{
			throw new UnableToStoreException();
		} 
		return add;
	}
	
	@Override
	public CustomerProfile getCustomerById(Integer id) throws NullPointerException{
		CustomerProfile customer = CustomerRepository.findById(id).get();
		if(customer==null)
		{
			throw new NullPointerException();
		}
		
		return customer;
	}

	@Override
	public List<CustomerProfile> getCustomers() throws NullPointerException{
		List<CustomerProfile> customers =CustomerRepository.getAllCustomers();
		if(customers.isEmpty())
		{
			throw new NullPointerException();
		}
		return customers;
	}
	
	@Override
	public void deleteCustomer(Integer id) {
		this.CustomerRepository.deleteById(id);
	}


	@Override
	public CustomerProfile LoginCustomer(String email, String password) throws NullPointerException, IncorrectPaswordException {
		CustomerProfile cust=null;
		CustomerLogin customerLogin1=customerLoginRepository.findByEmail(email);
		String pass2=customerLogin1.getPassword();
		if(pass2.equals(password))
		{
		    cust=customerLogin1.getCustomer();
		    if(cust==null)
			{
				throw new NullPointerException();
			}
		}
		else
		{
			throw new IncorrectPaswordException();	
		}
	return cust;
	}
	
    @Override
	public CustomerLogin getCustomerByemail(String email) throws NullPointerException {
		CustomerLogin customer=this.customerLoginRepository.findByEmail(email);
		if(customer==null)
		{
			throw new NullPointerException();
		}
		return customer;
	}

}
