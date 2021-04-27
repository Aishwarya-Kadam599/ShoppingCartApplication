package com.product.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

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

import com.product.entities.Product;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceImplTest {
	
	@Mock
	ProductRepository productRepository;
	
	@InjectMocks
	ProductServiceImpl serviceImpl;
	
	Product product=new Product("Reliance T-shirt","full slevees T-shirt","T-shirt","Red",12000l);

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void createProduct_Success_Test() throws NullPointerException {
		when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
		Product responseProduct=serviceImpl.createProduct(product);
		assertNotNull(responseProduct);
	}
	
	@Test
	void createProduct_Failure_Test() throws NullPointerException{
		when(productRepository.save(Mockito.any(Product.class))).thenReturn(null);
		assertThrows(NullPointerException.class,()->serviceImpl.createProduct(product));
	}
	
	@Test
	void getProductById_Success_Test() {
		when(productRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(product));
		Product responseProduct=serviceImpl.getProduct(0);
		assertNotNull(responseProduct);
	}
	
	@Test
	void getProductById_Failure_Test() {
		when(productRepository.findById(Mockito.anyInt())).thenReturn(null);
		assertThrows(NullPointerException.class,()->serviceImpl.getProduct(0));
	}
	
	@Test
	void getProducts_Success_Test() {
		when(productRepository.loadAll()).thenReturn(Arrays.asList(product,new Product()));
		List<Product> responseProduct=serviceImpl.getProducts();
		assertNotNull(responseProduct);
	}
	
	@Test
	void getProducts_Failure_Test() {
		when(productRepository.loadAll()).thenReturn(Arrays.asList());
		assertThrows(NullPointerException.class,()->serviceImpl.getProducts());
	}
	@Test
	void getProductsByColor_Success_Test() {
		when(productRepository.findByColor(Mockito.anyString())).thenReturn(Arrays.asList(product,new Product()));
		List<Product> responseProduct=serviceImpl.getProductsByColor("Red");
		assertNotNull(responseProduct);
	}
	
	@Test
	void getProductsByColor_Failure_Test() {
		when(productRepository.findByColor(Mockito.anyString())).thenReturn(Arrays.asList());
		assertThrows(NullPointerException.class,()->serviceImpl.getProductsByColor("Red"));
	}
	@Test
	void getProductsByCategory_Success_Test() {
		when(productRepository.findByCategory(Mockito.anyString())).thenReturn(Arrays.asList(product,new Product()));
		List<Product> responseProduct=serviceImpl.getProductsByCategory("T-shirt");
		assertNotNull(responseProduct);
	}
	
	@Test
	void getProductsByCategory_Failure_Test() {
		when(productRepository.findByCategory(Mockito.anyString())).thenReturn(Arrays.asList());
		assertThrows(NullPointerException.class,()->serviceImpl.getProductsByCategory("T-shirt"));
	}
	@Test
	void getProductsByColorAndCategory_Success_Test() {
		when(productRepository.findByColorAndCategory(Mockito.anyString(), Mockito.anyString())).thenReturn(Arrays.asList(product,new Product()));
		List<Product> responseProduct=serviceImpl.getProductsByColorAndCategory("Red","T-shirt");
		assertNotNull(responseProduct);
	}
	
	@Test
	void getProductsByColorAndCategory_Failure_Test() {
		when(productRepository.findByColorAndCategory(Mockito.anyString(),Mockito.anyString())).thenReturn(Arrays.asList());
		assertThrows(NullPointerException.class,()->serviceImpl.getProductsByColorAndCategory("Red","T-shirt"));
	}
	
	@Test
	void getProductsByName_Success_Test() {
		when(productRepository.findByName(Mockito.anyString())).thenReturn(Arrays.asList(product,new Product()));
		List<Product> responseProduct=serviceImpl.getProductsByName("T-shirt Reliance");
		assertNotNull(responseProduct);
	}
	
	@Test
	void getProductsByName_Failure_Test() {
		when(productRepository.findByName(Mockito.anyString())).thenReturn(Arrays.asList());
		assertThrows(NullPointerException.class,()->serviceImpl.getProductsByName("T-shirt Reliance"));
	}
	
	@Test
	void deleteProduct_Test()
	{
		doNothing().when(productRepository).deleteById(Mockito.anyInt());
		serviceImpl.deleteProduct(0);
	}

}
