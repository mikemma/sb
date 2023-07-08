package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.entity.Product;

public interface ProductRepository 
extends CrudRepository<Product, Long> {
// extends JpaRepository<Product,Long>{
    // Product[] findProducts();
    List<Product> findByName(String name);

}