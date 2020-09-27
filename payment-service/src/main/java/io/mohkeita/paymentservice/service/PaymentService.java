package io.mohkeita.paymentservice.service;

import io.mohkeita.paymentservice.entity.Payment;
import io.mohkeita.paymentservice.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository repository;

    public Payment doPayment(Payment payment) {
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return repository.save(payment);
    }

    private String paymentProcessing() {
        //api should 3rd payment gateway (paypal,paytm....)
        return new Random().nextBoolean()?"success":"false";
    }
}
