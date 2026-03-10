package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentControllerTest {

    private PaymentController paymentController;

    @BeforeEach
    void setUp() {
        paymentController = new PaymentController();
    }

    @Test
    void testCreatePaymentPage() {
        String viewName = paymentController.createPaymentPage();
        assertEquals("PaymentForm", viewName);
    }

    @Test
    void testPayOrderPage() {
        String viewName = paymentController.payOrderPage();
        assertEquals("PayOrder", viewName);
    }
}