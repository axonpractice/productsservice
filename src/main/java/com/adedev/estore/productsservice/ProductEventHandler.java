package com.adedev.estore.productsservice;

import com.adedev.estore.productsservice.core.data.ProductEntity;
import com.adedev.estore.productsservice.core.data.ProductRepository;
import com.adedev.estore.productsservice.core.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group") //group even handlers together
public class ProductEventHandler {

    private static final Logger log = LoggerFactory.getLogger(ProductEventHandler.class);
    private final ProductRepository productRepository;

    @EventHandler
    public void on (ProductCreatedEvent event) {
        log.info("Event handler for product created event.");
        ProductEntity product = new ProductEntity();
        BeanUtils.copyProperties(event, product);
        productRepository.save(product);
    }
}
