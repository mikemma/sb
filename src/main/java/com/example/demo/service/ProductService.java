package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public List<Product> getProducts() {
        List<Product> result = StreamSupport.stream(repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return result;
    }

    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    public List<Product> getProductsByName(String name) {
        return repository.findByName(name);
    }

    public Product addProduct(String name) {
        Product p = new Product();
        p.setName(name);
        return repository.save(p);
    }

    public Optional<Product> updateProduct(Long id, String newName) {
        Optional<Product> pOpt = getProductById(id);
        if (!pOpt.isPresent()) return Optional.empty();
        Product p = pOpt.get();
        p.setName(newName);
        return Optional.of(repository.save(p));
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

}
