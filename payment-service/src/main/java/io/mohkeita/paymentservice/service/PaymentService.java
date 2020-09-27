package io.mohkeita.paymentservice.service;

import io.mohkeita.paymentservice.entity.Payment;
import io.mohkeita.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    public Payment doPayment(Payment payment) {
        payment.setTransactionId(UUID.randomUUID().toString());
        return repository.save(payment);
    }
}