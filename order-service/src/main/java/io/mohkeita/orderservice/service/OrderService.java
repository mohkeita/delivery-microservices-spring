package io.mohkeita.orderservice.service;

import io.mohkeita.orderservice.common.Payment;
import io.mohkeita.orderservice.common.TransactionRequest;
import io.mohkeita.orderservice.entity.Order;
import io.mohkeita.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private RestTemplate template;

    public Order saveOrder(TransactionRequest request) {
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        // rest call
        return repository.save(order);
    }
}
