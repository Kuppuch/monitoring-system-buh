package com.kuppuch.repository;

import com.kuppuch.model.Payment;
import com.kuppuch.model.Work;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
}
