package com.example.shoppingcart.mappers;


import com.example.shoppingcart.entities.Product;
import com.example.shoppingcart.pojos.ProductData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Component
public class ProductMapper {


    public static List<Product> mapToEntity(List<ProductData> products) {

        List<Product> productList = new ArrayList<>();

        for(ProductData productData : products) {

            Product product = new Product();


            product.setProductName(productData.getProductName());
            product.setCity(productData.getCity());
            product.setAddress(productData.getAddress());
            product.setUsername(productData.getUsername());
            product.setCountry(productData.getCountry());
            product.setPaymentType(productData.getPaymentType());
            product.setState(productData.getState());

            productList.add(product);
        }

        return productList;
    }


    public static List<ProductData> mapToData(List<Product> products) {

        List<ProductData> productDataList = new ArrayList<>();

        for(Product product : products) {
            ProductData productData = new ProductData();

            productData.setAddress(product.getAddress());
            productData.setUsername(product.getUsername());
            productData.setProductName(product.getProductName());
            productData.setCity(product.getCity());
            productData.setCountry(product.getCountry());
            productData.setState(product.getState());
            productData.setPaymentType(product.getPaymentType());

            productDataList.add(productData);
        }
        return productDataList;
    }
}
