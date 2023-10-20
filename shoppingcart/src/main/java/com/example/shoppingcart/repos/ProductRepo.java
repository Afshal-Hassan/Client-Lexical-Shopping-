package com.example.shoppingcart.repos;

import com.example.shoppingcart.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {


    List<Product> findByUsername(String name);
}
