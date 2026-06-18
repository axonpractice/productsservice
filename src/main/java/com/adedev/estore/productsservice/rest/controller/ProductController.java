package com.adedev.estore.productsservice.rest.controller;

import com.adedev.estore.productsservice.command.CreateProductCommand;
import com.adedev.estore.productsservice.rest.model.CreateProductRestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final CommandGateway commandGateway;

    private final Environment environment;

    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel product) {
        var createProductCommand = CreateProductCommand.builder()
                .title(product.getTitle())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .productId(UUID.randomUUID().toString())
                .build();

        String returnValue;

        try {
            return commandGateway.sendAndWait(createProductCommand);
        } catch (Exception e) {
            return e.getMessage();
        }

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
