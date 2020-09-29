package io.mohkeita.orderservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mohkeita.orderservice.common.Payment;
import io.mohkeita.orderservice.common.TransactionRequest;
import io.mohkeita.orderservice.common.TransactionResponse;
import io.mohkeita.orderservice.entity.Order;
import io.mohkeita.orderservice.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    @Lazy
    private RestTemplate template;

    @Value("{microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_URL;
    private Logger log = LoggerFactory.getLogger(OrderService.class);

    public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
        String response = "";
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        log.info("OrderService request : {} ", new ObjectMapper().writeValueAsString(request));
        // rest call
        Payment paymentResponse = template.postForObject(ENDPOINT_URL, payment, Payment.class);
        log.info("Payment-service response from OrderService Rest call : {} ", new ObjectMapper().writeValueAsString(paymentResponse));

        response = paymentResponse.getPaymentStatus().equals("success")?"payment processing successful and order placed":"there is a failure in payment api, order added to cart";
        repository.save(order);
        return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
    }
}
