package com.adedev.estore.productsservice.rest.controller;

import com.adedev.estore.productsservice.rest.model.CreateProductRestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final Environment environment;

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel product) {
        return "HTTP POST handled " + product.getTitle();
    }

    @GetMapping
    public String getProduct() {
        return "HTTP GET handled " + environment.getProperty("local.server.port");
    }

    @PutMapping
    public String updateProduct() {
        return "HTTP PUT handled";
    }

    @DeleteMapping
    public String deleteProduct() {
        return "HTTP DELETE handled";
    }
}
