package com.example.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductsControllerTest {

	@Mock
	private ProductService productService;

	@InjectMocks
	ProductsController productsController;

	@Test
	public void testGetProducts() {
		Product product1 = new Product(1, "p1");
		Product product2 = new Product(2, "p2");
		List<Product> productList = Arrays.asList(product1, product2);

		when(productService.getProducts()).thenReturn(productList);

		List<Product> result = productsController.getProducts();

		assertThat(result.size()).isEqualTo(2);
		assertThat(result.get(0).getName()).isEqualTo(product1.getName());
		assertThat(result.get(1).getName()).isEqualTo(product2.getName());
	}

	@Test
	public void testGetProductById() {
		long id1 = 1;
		long id2 = 2;
		long id3 = 3;
		Optional<Product> product1 = Optional.of(new Product(id1, "p1"));
		Optional<Product> product2 = Optional.of(new Product(id2, "p2"));

		when(productService.getProductById(id1)).thenReturn(product1);
		when(productService.getProductById(id2)).thenReturn(product2);
		when(productService.getProductById(id3)).thenReturn(Optional.empty());

		ResponseEntity<Product> result = productsController.getProductById(id1);
		assertThat(result.getBody()).isEqualTo(product1.get());

		result = productsController.getProductById(id2);
		assertThat(result.getBody()).isEqualTo(product2.get());

		result = productsController.getProductById(id3);
		assertThat(result.getStatusCode().value()).isEqualTo(404);
	}

	@Test
	public void testGetProductByName() {
		String productName = "p1";
		Product product1 = new Product(1, productName);
		List<Product> productsList = Arrays.asList(product1);

		when(productService.getProductsByName(productName)).thenReturn(productsList);
		List<Product> result = productsController.getProductsByName(productName);

		assertThat(result.size()).isEqualTo(1);
		assertThat(result.get(0).getName()).isEqualTo(product1.getName());
	}

	@Test
	public void testAddProduct() {
		Product product = new Product(1, "p1");
		when(productService.addProduct(any(String.class))).thenReturn(product);

		ResponseEntity<Product> responseEntity = productsController.addProduct("p1");

		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		assertThat(responseEntity.getBody()).isEqualTo(product);
	}

	@Test
	public void testUpdateProduct() {
		long id1 = 1;
		long id2 = 2;
		String newName = "newP1";
		Product newProduct = new Product(id1, newName);

		when(productService.updateProduct(id1, newName)).thenReturn(Optional.of(newProduct));
		when(productService.updateProduct(id2, newName)).thenReturn(Optional.empty());

		ResponseEntity<Product> responseEntity = productsController.updateProduct(id1, newName);
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
		assertThat(responseEntity.getBody()).isEqualTo(newProduct);

		responseEntity = productsController.updateProduct(id2, newName);
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(404);
	}

	@Test
	public void testDeleteProduct() {
		long id1 = 1;
		doNothing().when(productService).deleteProduct(id1);

		ResponseEntity<Void> responseEntity = productsController.deleteProduct(id1);
		assertThat(responseEntity.getStatusCode().value()).isEqualTo(200);
	}
}
