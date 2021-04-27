package com.springBoot.microservices.shoppingcart.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.springBoot.microservices.shoppingcart.model.Cart;

@RunWith(MockitoJUnitRunner.class)
class CartServiceImplTest {
	@Mock
	private CartRepository cartRepository;

	@InjectMocks
	private CartServiceImpl serviceImpl;
	
	Cart cart=new Cart(1,3,5);
	
	@BeforeEach
	void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);

	}

	@Test
	void addToCart_Success_Test() throws NullPointerException {
		when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(cart);
		Cart responseCart=serviceImpl.addToCart(0, 0);
		assertNotNull(responseCart);
		
	}
	
	
	@Test
	void addToCart_Failure_Test() {
		when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(null);
		assertThrows(NullPointerException.class,()->serviceImpl.addToCart(0, 0));
	}
	
	@Test
	void getAllCarts_Success_Test() {
		when(cartRepository.findByCtid(Mockito.anyInt())).thenReturn(Arrays.asList(cart,new Cart()));
		List<Cart> responseCart=serviceImpl.getAllCarts(0);
		assertNotNull(responseCart);
	}
	
	@Test
	void getAllCarts_Failure_Test() {
		when(cartRepository.findByCtid(Mockito.anyInt())).thenReturn(null);
		assertThrows(NullPointerException.class,()->serviceImpl.getAllCarts(0));
	}
	
	@Test
	void deleteCart_Success_Test()
	{
		when(cartRepository.deleteByCtidAndPdid(Mockito.anyInt(),Mockito.anyInt())).thenReturn(1);
		int responseCart=serviceImpl.deleteFromCart(0, 0);
		assertEquals(1,responseCart);
	}
	@Test
	void deleteCart_Failure_Test()
	{
		when(cartRepository.deleteByCtidAndPdid(Mockito.anyInt(),Mockito.anyInt())).thenReturn(0);
		assertThrows(NullPointerException.class,()->serviceImpl.deleteFromCart(0, 0));
	}
}
