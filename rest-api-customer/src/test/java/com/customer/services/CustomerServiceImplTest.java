package com.customer.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.customer.entities.Address;
import com.customer.entities.CustomerLogin;
import com.customer.entities.CustomerProfile;
import com.customer.exceptions.IncorrectPaswordException;
import com.customer.exceptions.UnableToStoreException;

@RunWith(MockitoJUnitRunner.class)
class CustomerServiceImplTest {
	@Mock
	private CustomerLoginRepository customerLoginRepository;
	
	@Mock
	private CustomerProfileRepository customerRepository;

	@InjectMocks
	CustomerServiceImpl serviceImpl;
	
	Address address=new Address("Shri Nagar","Nanded","Maharashtra");
	CustomerLogin customerLogin=new CustomerLogin(1,"aishwaryakadam@gmail.com","aishu3211","User");
	CustomerProfile customer=new CustomerProfile(3, "Aishwarya","Kadam", "1234532728",address, Date.valueOf(LocalDate.of(1999,5,15)),customerLogin);
	
	//String jsonForm="{\"cp_id\":3,\"fname\":\"Aishwarya\",\"lname\":\"Kadam\",\"phno\":\"1234532728\",\"address\":{\"id\":0,\"street\":\"Shri Nagar\",\"city\":\"Nanded\",\"state\":\"Maharashtra\"},\"dob\":926706600000,\"customerLogin\":{\"id\":1,\"email\":\"aishwaryakadam@gmail.com\",\"password\":\"User\",\"role\":\"aishu3211\"}}";	


	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void createCustomer_Success_Test() throws NullPointerException, UnableToStoreException {
		when(customerRepository.save(Mockito.any(CustomerProfile.class))).thenReturn(customer);
		CustomerProfile responseCustomer=serviceImpl.createCustomer(customer);
		assertNotNull(responseCustomer);
	}
	
	
	@Test
	void createCustomer_Failure_Test() throws UnableToStoreException {
		when(customerRepository.save(Mockito.any(CustomerProfile.class))).thenReturn(null);
		assertThrows(UnableToStoreException.class,()->serviceImpl.createCustomer(customer));
	}
	
	@Test
	void getCustomerById_Success_Test() {
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customer));
		CustomerProfile responseCustomer=serviceImpl.getCustomerById(3);
		assertNotNull(responseCustomer);
	}
	
	@Test
	void getCustomerById_Failure_Test() {
		when(customerRepository.findById(Mockito.anyInt())).thenReturn(null);
		assertThrows(NullPointerException.class,()->serviceImpl.getCustomerById(3));
	}
	
	@Test
	void getCustomers_Success_Test() {
		when(customerRepository.getAllCustomers()).thenReturn(Arrays.asList(customer,new CustomerProfile()));
		List<CustomerProfile> responseCustomer=serviceImpl.getCustomers();
		assertNotNull(responseCustomer);
	}
	
	@Test
	void getCustomers_Failure_Test() {
		when(customerRepository.getAllCustomers()).thenReturn(Arrays.asList());
		assertThrows(NullPointerException.class,()->serviceImpl.getCustomers());
	}
	
	@Test
	void deleteCustomers_Test()
	{
		doNothing().when(customerRepository).deleteById(anyInt());
		serviceImpl.deleteCustomer(1);
	}
	@Test
	void LoginCustomer_Success_Test() throws NullPointerException, IncorrectPaswordException {
		String email="aishwaryakadam@gmail.com";
		String password="aishu3211";
		when(customerLoginRepository.findByEmail(Mockito.anyString())).thenReturn(customerLogin);
		customerLogin.setCustomer(customer);
		CustomerProfile responseCustomer=serviceImpl.LoginCustomer(email, password);
		assertNotNull(responseCustomer);
	}
	
	@Test
	void LoginCustomers_NullPointerException_Test() throws NullPointerException, IncorrectPaswordException {
		String email="aishwaryakadam@gmail.com";
		String password="aishu3211";
		when(customerLoginRepository.findByEmail(Mockito.anyString())).thenReturn(customerLogin);
		assertThrows(NullPointerException.class,()->serviceImpl.LoginCustomer(email, password));
	}
	@Test
	void LoginCustomers_IncorrectPasswordException_Test() throws NullPointerException, IncorrectPaswordException {
		String email="aishwaryakadam@gmail.com";
		String password="aishwarya";
		when(customerLoginRepository.findByEmail(Mockito.anyString())).thenReturn(customerLogin);
		assertThrows(IncorrectPaswordException.class,()->serviceImpl.LoginCustomer(email, password));
	}
	@Test
	void getCustomerByEmail_Success_Test() {
		String email="aishwaryakadam@gmail.com";
		when(customerLoginRepository.findByEmail(Mockito.anyString())).thenReturn(customerLogin);
		CustomerLogin responseCustomer=serviceImpl.getCustomerByemail(email);
		assertNotNull(responseCustomer);
	}
	
	@Test
	void getCustomerByEmail_Failure_Test() {
		String email="aishwaryakadam@gmail.com";
		when(customerLoginRepository.findByEmail(Mockito.anyString())).thenReturn(null);
		assertThrows(NullPointerException.class,()->serviceImpl.getCustomerByemail(email));
	}
}
