package com.customer.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.customer.entities.CustomerLogin;
import com.customer.entities.CustomerProfile;
import com.customer.exceptions.IncorrectPaswordException;
import com.customer.exceptions.UnableToStoreException;
import com.customer.services.CustomerServiceImpl;

@RestController
public class CustomerController {
	@Autowired
	private CustomerServiceImpl dao;
	
	@GetMapping("/customer/id/{id}")
	public ResponseEntity<CustomerProfile> customerById(@PathVariable("id") Integer id) {
		CustomerProfile customer = this.dao.getCustomerById(id);
		return ResponseEntity.of(Optional.of(customer));
	}

	@GetMapping("/customers")
	public ResponseEntity<List<CustomerProfile>> customers() {
		List<CustomerProfile> customers = this.dao.getCustomers();
		return ResponseEntity.of(Optional.of(customers));
	}

	@PostMapping("/register")
	public ResponseEntity<CustomerProfile> createCustomer(@Valid @RequestBody CustomerProfile customer) throws NullPointerException, UnableToStoreException {
		CustomerProfile b = this.dao.createCustomer(customer);
		return ResponseEntity.of(Optional.of(b));
	}

	@DeleteMapping("/customer/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") Integer id) {
		this.dao.deleteCustomer(id);
		return ResponseEntity.ok("deleted successsfully");
	}

	@PutMapping("/customer/{id}")
	public ResponseEntity<CustomerProfile> updateCustomer(@RequestBody CustomerProfile customer,
			@PathVariable("id") Integer id) throws NullPointerException, UnableToStoreException {
		if(customer.getCp_id()!=id)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		CustomerProfile b = this.dao.createCustomer(customer);
		return ResponseEntity.of(Optional.of(b));
	}
	@GetMapping("/customer/{email}")
	public CustomerLogin customerByUsername(@PathVariable("email") String email)
	{
		CustomerLogin customer = this.dao.getCustomerByemail(email);
		return customer;
	}
	
	@PostMapping("/login")
	public ResponseEntity<CustomerProfile> login(@Valid @RequestBody CustomerLogin customerLogin) throws NullPointerException, IncorrectPaswordException {
		CustomerProfile b = this.dao.LoginCustomer(customerLogin.getEmail(), customerLogin.getPassword());
		return ResponseEntity.of(Optional.of(b));
	}
}
