package com.kuppuch.web;

import com.kuppuch.model.Payment;
import com.kuppuch.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping("/paydone")
    public String pay(@RequestParam Payment payment) {
        paymentRepository.save(payment);
        return "pay/pay_done";
    }
}
