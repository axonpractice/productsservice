package com.adedev.estore.productsservice.command;

import com.adedev.core.command.ReserveProductCommand;
import com.adedev.core.event.ProductReservedEvent;
import com.adedev.estore.productsservice.core.event.ProductCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;


@Aggregate
@NoArgsConstructor
@Log
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;

    private String title;

    private BigDecimal price;

    private Integer quantity;

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand) {
        log.info("CreateProductCommand product aggregate reached.");
        log.info("Validating createProductCommand.");

        if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be less than 0.");
        }

        if (createProductCommand.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null.");
        }

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);
    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) {
        log.info("Handling ReserveProductCommand for productId: " + this.productId);
        if (quantity < reserveProductCommand.getQuantity()) {
            throw new IllegalArgumentException("Not sufficient items in stock.");
        }

        ProductReservedEvent productReservedEvent = ProductReservedEvent.builder()
                .productId(reserveProductCommand.getProductId())
                .orderId(reserveProductCommand.getOrderId())
                .quantity(reserveProductCommand.getQuantity())
                .userId(reserveProductCommand.getUserId())
                .build();

        AggregateLifecycle.apply(productReservedEvent);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        log.info("ProductCreatedEvent triggered.");
        this.productId = productCreatedEvent.getProductId();
        this.price = productCreatedEvent.getPrice();
        this.title = productCreatedEvent.getTitle();
        this.quantity = productCreatedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent) {
        log.info("Handling ProductReservedEvent for productId: " + this.productId);
        this.quantity = this.quantity - productReservedEvent.getQuantity();
    }
}
