package com.example.shoppingcart.controllers;


import com.example.shoppingcart.pojos.ProductData;
import com.example.shoppingcart.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService service;



    @PostMapping("/save")
    public void saveProductData(@RequestBody List<ProductData> productData) {
        service.saveProductDetails(productData);
    }



    @GetMapping("/get/{username}")
    public List<ProductData> getProductsByUsername(@PathVariable("username")String username) {
        return service.getProductsByUsername(username);
    }
}
