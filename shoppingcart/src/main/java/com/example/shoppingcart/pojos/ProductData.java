package com.example.shoppingcart.pojos;



import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {

    @JsonProperty("username")
    private String username;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty( "country")
    private String country;

    @JsonProperty("state")
    private String state;

    @JsonProperty("city")
    private String city;

    @JsonProperty("address")
    private String address;

    @JsonProperty("payment_type")
    private String paymentType;
}
