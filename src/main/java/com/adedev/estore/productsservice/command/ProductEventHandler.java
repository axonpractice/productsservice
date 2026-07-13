package com.adedev.estore.productsservice.command;

import com.adedev.core.event.ProductReservedEvent;
import com.adedev.estore.productsservice.core.data.ProductEntity;
import com.adedev.estore.productsservice.core.data.ProductRepository;
import com.adedev.estore.productsservice.core.event.ProductCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
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

    @ExceptionHandler(resultType = Exception.class)
    public void handle(Exception e) throws Exception {
        throw e;
    }

    @ExceptionHandler(resultType = IllegalStateException.class)
    public void handle(IllegalArgumentException e) {
        log.error(e.getMessage());
    }

    @EventHandler
    public void on (ProductCreatedEvent event) throws Exception {
        log.info("Event handler for product created event.");
        ProductEntity product = new ProductEntity();
        BeanUtils.copyProperties(event, product);

        try {
            productRepository.save(product);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

//        //Wrapped in CommandExecutionException
//        throw new Exception("Test exception");
    }

    @EventHandler
    protected void on(ProductReservedEvent productReservedEvent) {
        var product = productRepository.findByProductId(productReservedEvent.getProductId());
        product.setQuantity(productReservedEvent.getQuantity());
        productRepository.save(product);
    }
}
