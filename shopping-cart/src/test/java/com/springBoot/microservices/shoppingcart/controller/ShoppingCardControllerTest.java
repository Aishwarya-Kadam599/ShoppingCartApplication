package com.springBoot.microservices.shoppingcart.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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

import com.springBoot.microservices.shoppingcart.model.Cart;
import com.springBoot.microservices.shoppingcart.services.CartServiceImpl;
import com.springBoot.microservices.shoppingcart.services.CustomerProxy;
import com.springBoot.microservices.shoppingcart.services.ProductProxy;
import com.springBoot.microservices.shoppingcart.model.Product;

@RunWith(MockitoJUnitRunner.class)
class ShoppingCardControllerTest {
	@InjectMocks
	private ShoppingCardController shoppingCardController;
	
	@Mock
	private ProductProxy proxy1;
    
	@Mock
	private CartServiceImpl cartService;
	
	private MockMvc mockMvc;
	
	Cart cart=new Cart(1,3,5);
	Product product=new Product("Reliance T-shirt","full slevees T-shirt","T-shirt","Red",12000l);
	
	@BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCardController).build();
	}
	@Test
	void addToCartTest() throws Exception {
		when(cartService.addToCart(anyInt(),anyInt())).thenReturn(cart);
		mockMvc.perform(MockMvcRequestBuilders.post("/add/3/5")
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isOk());
		
	}
	@Test
	void getFromCartTest() throws Exception {
		when(cartService.getAllCarts(anyInt())).thenReturn(Arrays.asList(cart,new Cart(3,3,6)));
		when(proxy1.getProduct(anyInt())).thenReturn(product);
		mockMvc.perform(MockMvcRequestBuilders.get("/cart/3")
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
                .andExpect(status().isOk());
		
	}
	@Test
	void deleteCustomerTest() throws Exception {
		
		when(cartService.deleteFromCart(anyInt(),anyInt())).thenReturn(1);
		MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.delete("/cart/3/5")
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		   String content = mvcResult.getResponse().getContentAsString();
		   assertEquals(content, "deleted successsfully");
	}

}
