package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Payment {
    String id;
    Order order;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, Order order, String method, String status, Map<String, String> paymentData) {
    }

    public Payment(String id, String method, Map<String, String> paymentData, String status) {
    }

    public void setStatus(String status) {
    }
}