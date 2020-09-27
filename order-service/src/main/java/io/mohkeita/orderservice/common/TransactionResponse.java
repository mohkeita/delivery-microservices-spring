package io.mohkeita.orderservice.common;

import io.mohkeita.orderservice.entity.Order;

public class TransactionResponse {
    private Order order;
    private double amount;
    private String transactionId;
}
