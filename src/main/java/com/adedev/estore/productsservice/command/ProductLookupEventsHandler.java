package com.adedev.estore.productsservice.command;

import com.adedev.estore.productsservice.core.data.ProductLookupEntity;
import com.adedev.estore.productsservice.core.data.ProductLookupRepository;
import com.adedev.estore.productsservice.core.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("product-group") //group even handlers together. all events are handled only once and are easy to rollback.
public class ProductLookupEventsHandler {

    private static final Logger log = LoggerFactory.getLogger(ProductLookupEventsHandler.class);
    private final ProductLookupRepository productLookupRepository;

    @EventHandler
    public void on(ProductCreatedEvent event) {
        log.info("Product lookup event handler reached.");

        ProductLookupEntity entity = new ProductLookupEntity(event.getProductId(), event.getTitle());

        productLookupRepository.save(entity);
    }
}
