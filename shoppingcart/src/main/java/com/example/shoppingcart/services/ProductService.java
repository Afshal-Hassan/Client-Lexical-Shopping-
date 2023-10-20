package com.example.shoppingcart.services;

import com.example.shoppingcart.mappers.ProductMapper;
import com.example.shoppingcart.pojos.ProductData;
import com.example.shoppingcart.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;



    public void saveProductDetails(List <ProductData> productData) {
        repo.saveAll(ProductMapper.mapToEntity(productData));
    }



    public List<ProductData> getProductsByUsername(String username) {
        return ProductMapper.mapToData(repo.findByUsername(username));
    }
}
