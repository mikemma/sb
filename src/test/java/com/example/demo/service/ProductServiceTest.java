package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.example.demo.controller.ProductsController;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    ProductService productsService;

    @Test
    public void testUpdateExistingProduct() {
        long id1 = 1;
        String newName = "newName";
        Product product = new Product(id1, "name");
        Product newProduct = new Product(id1, newName);
        when(productRepository.findById(id1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        Optional<Product> prodOpt = productsService.updateProduct(id1, newName);
        assertTrue(prodOpt.isPresent());
        assertThat(prodOpt.get()).isEqualTo(newProduct);
    }

    @Test
    public void testUpdateNonExistingProduct() {
        long id1 = 1;
        String newName = "newName";
        when(productRepository.findById(id1)).thenReturn(Optional.empty());

        Optional<Product> prodOpt = productsService.updateProduct(id1, newName);
        assertFalse(prodOpt.isPresent());
    }

}
