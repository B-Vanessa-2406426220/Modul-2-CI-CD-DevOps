package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private PaymentService paymentService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateOrderPage() {
        String viewName = orderController.createOrderPage();
        assertEquals("CreateOrder", viewName);
    }

    @Test
    void testCreateOrder() {
        String viewName = orderController.createOrder("Vanessa");

        verify(orderService).createOrder(any(Order.class));
        assertEquals("redirect:/order/history", viewName);
    }

    @Test
    void testHistoryPage() {
        String viewName = orderController.historyPage();
        assertEquals("OrderHistoryForm", viewName);
    }

    @Test
    void testViewHistory() {
        List<Order> mockOrders = new ArrayList<>();
        when(orderService.findAllByAuthor("Vanessa")).thenReturn(mockOrders);

        String viewName = orderController.viewHistory("Vanessa", model);

        verify(model).addAttribute("orders", mockOrders);
        verify(model).addAttribute("author", "Vanessa");
        assertEquals("OrderList", viewName);
    }

    @Test
    void testPayOrderPage() {
        Order mockOrder = mock(Order.class);
        when(orderService.findById("order-123")).thenReturn(mockOrder);

        String viewName = orderController.payOrderPage("order-123", model);

        verify(model).addAttribute("order", mockOrder);
        assertEquals("PayOrder", viewName);
    }

    @Test
    void testPayOrder() {
        Order mockOrder = mock(Order.class);
        Payment mockPayment = mock(Payment.class);
        Map<String, String> dummyData = new HashMap<>();
        dummyData.put("voucherCode", "ESHOP123");

        when(orderService.findById("order-123")).thenReturn(mockOrder);
        when(paymentService.addPayment(mockOrder, "VOUCHER", dummyData)).thenReturn(mockPayment);
        when(mockPayment.getId()).thenReturn("payment-123");

        String viewName = orderController.payOrder("order-123", "VOUCHER", dummyData, model);

        verify(model).addAttribute("paymentId", "payment-123");
        assertEquals("PaymentIdResult", viewName);
    }
}