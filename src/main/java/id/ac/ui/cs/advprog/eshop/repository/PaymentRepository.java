package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private Map<String, Payment> paymentsData = new HashMap<>();

    public Payment save(Payment payment) {
        paymentsData.put(payment.getId(), payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        return paymentsData.get(paymentId);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentsData.values());
    }
}