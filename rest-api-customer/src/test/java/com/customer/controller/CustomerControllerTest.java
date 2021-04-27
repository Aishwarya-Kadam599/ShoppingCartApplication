
package com.customer.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import javax.ws.rs.core.Response.Status;

//import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.customer.entities.Address;
import com.customer.entities.CustomerLogin;
import com.customer.entities.CustomerProfile;
import com.customer.services.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
class CustomerControllerTest {
	
	@InjectMocks
	private CustomerController customerController;
	
	@Mock
	private CustomerServiceImpl customerService;

	private MockMvc mockMvc;
	
	Address address=new Address("Shri Nagar","Nanded","Maharashtra");
	CustomerLogin customerLogin=new CustomerLogin(1,"aishwaryakadam@gmail.com","aishu3211","User");
	CustomerProfile customer=new CustomerProfile(3, "Aishwarya","Kadam", "1234532728",address, Date.valueOf(LocalDate.of(1999,5,15)),customerLogin);
	
	String jsonForm="{\"cp_id\":3,\"fname\":\"Aishwarya\",\"lname\":\"Kadam\",\"phno\":\"1234532728\",\"address\":{\"id\":0,\"street\":\"Shri Nagar\",\"city\":\"Nanded\",\"state\":\"Maharashtra\"},\"dob\":926706600000,\"customerLogin\":{\"id\":1,\"email\":\"aishwaryakadam@gmail.com\",\"password\":\"User\",\"role\":\"aishu3211\"}}";	

	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}

	@Test
	void getCustomerByIdTest() throws Exception
	{
		when(customerService.getCustomerById(anyInt())).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/id/3")
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fname").value("Aishwarya"))
                .andReturn();
		
		//assertEquals(jsonForm,result.getResponse().getContentAsString());
	}
	
	@Test
	void getCustomersTest() throws Exception
	{
		when(customerService.getCustomers()).thenReturn(Arrays.asList(customer,new CustomerProfile()));
		mockMvc.perform(MockMvcRequestBuilders.get("/customers")
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isOk());
        
	}
	
	
	@Test
	void registerCustomerTest_Valid_Data() throws Exception {
		when(customerService.createCustomer(any(CustomerProfile.class))).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.post("/register").content(jsonForm)
		.contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
		.andDo(print())
        .andExpect(status().isOk());
		
	}
	
	@Test
	void deleteCustomerTest() throws Exception {
		
		doNothing().when(customerService).deleteCustomer(anyInt());
		MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.delete("/customer/3")
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		   String content = mvcResult.getResponse().getContentAsString();
		   assertEquals(content, "deleted successsfully");
	}
	
	@Test
	void updateCustomerTest_Equals_Id() throws Exception
	{
		when(customerService.createCustomer(any(CustomerProfile.class))).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.put("/customer/3")
				.content(jsonForm)
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cp_id").value(3));
	}
	
	@Test
	void updateCustomerTest_Not_Equals_Id() throws Exception
	{
		customer.setCp_id(2);
		when(customerService.createCustomer(any(CustomerProfile.class))).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.put("/customer/2")
				.content(jsonForm)
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isBadRequest());
	}
	@Test
	void getCustomerByUsernameTest() throws Exception
	{
		when(customerService.getCustomerByemail(anyString())).thenReturn(customerLogin);
		mockMvc.perform(MockMvcRequestBuilders.get("/customer/aishwaryakadam@gmail.com")
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("aishwaryakadam@gmail.com"));
	}
	@Test
	void getCustomerForLoginTest() throws Exception
	{
		String CustomerLoginJson="{\"id\":1,\"email\":\"aishwaryakadam@gmail.com\",\"password\":\"User\",\"role\":\"aishu3211\"}";
		when(customerService.LoginCustomer(anyString(),anyString())).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.content(CustomerLoginJson)
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerLogin.email").value("aishwaryakadam@gmail.com"));
	}

}
