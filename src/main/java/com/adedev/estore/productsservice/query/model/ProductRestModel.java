package com.adedev.estore.productsservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ProductRestModel {

    private String productId;

    private String title;

    private BigDecimal price;

    private Integer quantity;

}
