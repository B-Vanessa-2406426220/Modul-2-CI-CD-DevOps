package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

    @GetMapping("/payment/create")
    public String createPaymentPage() {
        return "PaymentForm";
    }

    @GetMapping("/order/pay")
    public String payOrderPage() {
        return "PayOrder";
    }
}