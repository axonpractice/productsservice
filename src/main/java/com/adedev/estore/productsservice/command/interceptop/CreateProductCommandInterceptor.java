package com.adedev.estore.productsservice.command.interceptop;

import com.adedev.estore.productsservice.command.CreateProductCommand;
import com.adedev.estore.productsservice.core.data.ProductLookupRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private static final Logger log = LoggerFactory.getLogger(CreateProductCommandInterceptor.class);
    private final ProductLookupRepository productLookupRepository;

    @Nonnull
    @Override
    public CommandMessage<?> handle(@Nonnull CommandMessage<?> message) {
        return MessageDispatchInterceptor.super.handle(message);
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        log.info("Reached create product command interceptor.");

        return (index, command) -> {
            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                var createProductCommand = (CreateProductCommand) command.getPayload();
                if (createProductCommand.getTitle().isEmpty()) {
                    throw new IllegalArgumentException("Title cannot be empty.");
                }

                var existingProduct = productLookupRepository.findByProductIdOrTitle(createProductCommand.getProductId(), createProductCommand.getTitle());

                if (!Objects.isNull(existingProduct)) {
                    throw new IllegalArgumentException("Product already exists.");
                }
            }

            return command;
        };
    }
}
