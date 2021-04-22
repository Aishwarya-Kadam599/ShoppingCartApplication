package com.product.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.product.entities.Product;
import com.product.services.ProductServiceImpl;

@RunWith(MockitoJUnitRunner.class)
class ProductControllerTest {

	@InjectMocks
	ProductController productController;
	
	@Mock
	ProductServiceImpl productService;
	
	private MockMvc mockMvc;
	
	Product product=new Product("Reliance T-shirt","full slevees T-shirt","T-shirt","Red",12000l);
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc=MockMvcBuilders.standaloneSetup(productController).build();
	}
	
	
	
	@Test
	void getProductById() throws Exception
	{
		when(productService.getProduct(anyInt())).thenReturn(product);
		mockMvc.perform(MockMvcRequestBuilders.get("/product/3")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void getAllProducts() throws Exception
	{
		when(productService.getProducts()).thenReturn(Arrays.asList(product,new Product()));
		mockMvc.perform(MockMvcRequestBuilders.get("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void getProductsByColorTest() throws Exception
	{
		when(productService.getProductsByColor(anyString())).thenReturn(Arrays.asList(product,new Product()));
		mockMvc.perform(MockMvcRequestBuilders.get("/products/color/Red")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	@Test
	void getProductsByNameTest() throws Exception
	{
		when(productService.getProductsByName(anyString())).thenReturn(Arrays.asList(product,new Product()));
		mockMvc.perform(MockMvcRequestBuilders.get("/products/name/Reliance T-shirt")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void getProductsByCategoryTest() throws Exception
	{
		when(productService.getProductsByCategory(anyString())).thenReturn(Arrays.asList(product,new Product()));
		mockMvc.perform(MockMvcRequestBuilders.get("/products/category/T-shirt")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void getProductsByColorAndCategoryTest() throws Exception
	{
		when(productService.getProductsByColorAndCategory(anyString(),anyString())).thenReturn(Arrays.asList(product,new Product()));
		mockMvc.perform(MockMvcRequestBuilders.get("/products/Red/T-shirt")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	void addProductTest() throws Exception
	{
		String JsonProduct= "{\"id\":3,\"name\":\"Reliance T-shirt\",\"description\":\"full slevees T-shirt\",\"category\":\"T-shirt\",\"color\":\"Red\",\"price\":1200}";
		when(productService.createProduct(any(Product.class))).thenReturn(product);
		mockMvc.perform(MockMvcRequestBuilders.post("/add")
				.content(JsonProduct)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	
	}
	
	@Test
	void updateProductTest() throws Exception
	{
		String JsonProduct= "{\"id\":3,\"name\":\"Reliance T-shirt\",\"description\":\"full slevees T-shirt\",\"category\":\"T-shirt\",\"color\":\"Red\",\"price\":1200}";
		when(productService.createProduct(any(Product.class))).thenReturn(product);
		mockMvc.perform(MockMvcRequestBuilders.put("/update/3")
				.content(JsonProduct)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	
	}
	
	@Test
	void deleteProductTest() throws Exception {
		
		doNothing().when(productService).deleteProduct(anyInt());
		MvcResult mvcResult=mockMvc.perform(MockMvcRequestBuilders.delete("/delete/3")
				.contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andReturn();
		   String content = mvcResult.getResponse().getContentAsString();
		   assertEquals("Successfull Deleted!!",content);
	}

}
