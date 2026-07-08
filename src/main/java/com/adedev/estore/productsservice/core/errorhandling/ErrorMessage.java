package com.adedev.estore.productsservice.core.errorhandling;

import lombok.Data;

import java.time.LocalDateTime;

public record ErrorMessage(LocalDateTime timestamp, String message) {
}
