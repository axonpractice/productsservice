package com.adedev.estore.productsservice.query.rest;

import com.adedev.estore.productsservice.query.FindProductsQuery;
import com.adedev.estore.productsservice.query.model.ProductRestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductQueryController {

    private final QueryGateway queryGateway;

    private static final Logger log = LoggerFactory.getLogger(ProductQueryController.class);

    @GetMapping
    public List<ProductRestModel> getProducts() {
        log.info("Reached get products query controller.");

        FindProductsQuery findProductsQuery = new FindProductsQuery();

        return  queryGateway.query(findProductsQuery,
               ResponseTypes.multipleInstancesOf(ProductRestModel.class)).join();

    }
}
