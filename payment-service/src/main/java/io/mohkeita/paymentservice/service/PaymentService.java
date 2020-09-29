package io.mohkeita.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mohkeita.paymentservice.entity.Payment;
import io.mohkeita.paymentservice.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    private Logger log = LoggerFactory.getLogger(PaymentService.class);

    public Payment doPayment(Payment payment) throws JsonProcessingException {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        log.info("PaymentService request : {} ", new ObjectMapper().writeValueAsString(payment));
        return repository.save(payment);
    }

    private String paymentProcessing() {
        //api should 3rd payment gateway (paypal,paytm....)
        return new Random().nextBoolean()?"success":"false";
    }

    public Payment findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
        Payment  payment = repository.findByOrderId(orderId);
        log.info("PaymentService findPaymentHistoryByOrderId : {} ", new ObjectMapper().writeValueAsString(payment));
        return payment;
    }
}
