package com.adedev.estore.productsservice.command.interceptop;

import com.adedev.estore.productsservice.command.CreateProductCommand;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    @Nonnull
    @Override
    public CommandMessage<?> handle(@Nonnull CommandMessage<?> message) {
        return MessageDispatchInterceptor.super.handle(message);
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if (CreateProductCommand.class.equals(command.getPayloadType())) {
                var createProductCommand = (CreateProductCommand) command.getPayload();
                if (createProductCommand.getTitle().isEmpty()) {
                    throw new IllegalArgumentException("Title cannot be empty.");
                }
            }

            return command;
        };
    }
}
