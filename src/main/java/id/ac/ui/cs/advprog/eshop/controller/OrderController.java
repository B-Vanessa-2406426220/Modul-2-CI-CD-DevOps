package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage() { return "CreateOrder"; }

    @PostMapping("/create")
    public String createOrder(@RequestParam String author) {
        List<Product> dummyProducts = new ArrayList<>();
        Product dummyProduct = new Product();
        dummyProduct.setProductId("dummy-123");
        dummyProduct.setProductName("Dummy Product");
        dummyProduct.setProductQuantity(1);
        dummyProducts.add(dummyProduct);

        Order order = new Order(UUID.randomUUID().toString(), dummyProducts, System.currentTimeMillis(), author);
        orderService.createOrder(order);

        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String historyPage() { return "OrderHistoryForm"; }

    @PostMapping("/history")
    public String viewHistory(@RequestParam String author, Model model) {
        model.addAttribute("orders", orderService.findAllByAuthor(author));
        model.addAttribute("author", author);
        return "OrderList";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        model.addAttribute("order", orderService.findById(orderId));
        return "PayOrder";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(@PathVariable String orderId, @RequestParam String method, @RequestParam Map<String, String> data, Model model) {
        Payment payment = paymentService.addPayment(orderService.findById(orderId), method, data);
        model.addAttribute("paymentId", payment.getId());
        return "PaymentIdResult";
    }
}