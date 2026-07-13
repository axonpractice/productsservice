package com.adedev.estore.productsservice.command.rest.controller;

import com.adedev.estore.productsservice.command.CreateProductCommand;
import com.adedev.estore.productsservice.command.rest.model.CreateProductRestModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductCommandController {

    private final CommandGateway commandGateway;

    @PostMapping
    public String createProduct(@RequestBody @Valid CreateProductRestModel product) {
        var createProductCommand = CreateProductCommand.builder()
                .title(product.getTitle())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .productId(UUID.randomUUID().toString())
                .build();

        return commandGateway.sendAndWait(createProductCommand);

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
