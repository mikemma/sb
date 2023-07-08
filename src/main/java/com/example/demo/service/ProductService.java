package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Iterable<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
    public Iterable<Product> getProductsByName(String name) {
        return repository.findByName(name);
    }

    public Product addProduct(String name) {
        Product p = new Product();
        p.setName(name);
        return repository.save(p);
    }

    public Optional<Product> updateProduct(Long id, String newName) {
        Optional<Product> productOpt = repository.findById(id);
        if (!productOpt.isPresent()) {return  Optional.empty();}
                //.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        Product p = productOpt.get();
                p.setName(newName);
        return Optional.of(repository.save(p));
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    

    
}
