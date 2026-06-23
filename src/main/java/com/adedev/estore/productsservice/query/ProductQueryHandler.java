package com.adedev.estore.productsservice.query;

import com.adedev.estore.productsservice.core.data.ProductRepository;
import com.adedev.estore.productsservice.query.model.ProductRestModel;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductQueryHandler {

    private static final Logger log = LoggerFactory.getLogger(ProductQueryHandler.class);
    private final ProductRepository productRepository;

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery query) {
        log.info("Reached product query handler.");
        List<ProductRestModel> productRestModels = new ArrayList<>();
        var dbProducts = productRepository.findAll();

        dbProducts.forEach(p -> productRestModels.add(
                new ProductRestModel(p.getProductId(), p.getTitle(), p.getPrice(), p.getQuantity())));

        return productRestModels;
    }
}
