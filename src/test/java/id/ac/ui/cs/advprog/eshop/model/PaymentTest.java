package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {
    private Payment payment;
    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("1b709a33-4da3-4cbd-abd1-dc4dd3d855eb");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order("c564148a-a11f-4865-9744-60ade538c37b", products,
                System.currentTimeMillis(), "V", OrderStatus.WAITING_PAYMENT.getValue());

        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");

        payment = new Payment("1424f2b7-2af2-4b6e-a43b-a25cb252e958", order,
                "VOUCHER", "SUCCESS", paymentData);
    }

    @Test
    void testPaymentCreation() {
        assertEquals("1424f2b7-2af2-4b6e-a43b-a25cb252e958", payment.getId());
        assertEquals(order, payment.getOrder());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testSetOrder() {
        List<Product> newProducts = new ArrayList<>();
        Product newProduct = new Product();
        newProduct.setProductId("new-product-id");
        newProduct.setProductName("New Product");
        newProduct.setProductQuantity(1);
        newProducts.add(newProduct);

        Order newOrder = new Order("new-order-id", newProducts,
                System.currentTimeMillis(), "Setiawan", OrderStatus.WAITING_PAYMENT.getValue());

        payment.setOrder(newOrder);
        assertEquals(newOrder, payment.getOrder());
        assertNotEquals(order, payment.getOrder());
    }

    @Test
    void testSetMethod() {
        payment.setMethod("BANK_TRANSFER");
        assertEquals("BANK_TRANSFER", payment.getMethod());
    }

    @Test
    void testSetStatus() {
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetPaymentData() {
        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("voucherCode", "ESHOP87654321XYZ");

        payment.setPaymentData(newPaymentData);
        assertEquals(newPaymentData, payment.getPaymentData());
        assertNotEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testInvalidVoucherCode() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "INVALID12345");

        payment.setPaymentData(invalidVoucherData);
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testEmptyVoucherCode() {
        Map<String, String> emptyVoucherData = new HashMap<>();
        emptyVoucherData.put("voucherCode", "");

        payment.setPaymentData(emptyVoucherData);
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testNullVoucherCode() {
        Map<String, String> nullVoucherData = new HashMap<>();
        nullVoucherData.put("voucherCode", null);

        payment.setPaymentData(nullVoucherData);
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testVoucherThatDoesNotStartWithESHOP() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "SHOP1234567890AB");

        payment.setPaymentData(invalidVoucherData);
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testVoucherShorterThan16Characters() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "ESHOP12345");

        payment.setPaymentData(invalidVoucherData);
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testVoucherLongerThan16Characters() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "ESHOP12345678901234");

        payment.setPaymentData(invalidVoucherData);
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testVoucherWithLessThan8Numbers() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "ESHOP123ABCDEFGHI");

        payment.setPaymentData(invalidVoucherData);
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testVoucherWithMoreThan8Numbers() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "ESHOP1234567890AB");

        payment.setPaymentData(invalidVoucherData);
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testVoucherCodeWithLowerCasePrefix() {
        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "eshop12345678ABCD");

        payment.setPaymentData(invalidVoucherData);
        assertEquals("ESHOP12345678ABC", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testBankTransferConstructorWithMissingBankName() {
        Map<String, String> invalidBankData = new HashMap<>();
        invalidBankData.put("referenceCode", "REF123456789");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment("bank-payment-id", order,
                    "TRANSFER_BANK", "SUCCESS", invalidBankData);
        });

        assertEquals("paymentData is not valid", exception.getMessage());
    }

    @Test
    void testBankTransferConstructorWithMissingReferenceCode() {
        Map<String, String> invalidBankData = new HashMap<>();
        invalidBankData.put("bankName", "BCA");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment("bank-payment-id", order,
                    "TRANSFER_BANK", "SUCCESS", invalidBankData);
        });

        assertEquals("paymentData is not valid", exception.getMessage());
    }

    @Test
    void testBankTransferConstructorWithNullData() {
        Map<String, String> invalidBankData = new HashMap<>();
        invalidBankData.put("bankName", null);
        invalidBankData.put("referenceCode", null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment("bank-payment-id", order,
                    "TRANSFER_BANK", "SUCCESS", invalidBankData);
        });

        assertEquals("paymentData is not valid", exception.getMessage());
    }

    @Test
    void testBankTransferConstructorWithEmptyData() {
        Map<String, String> invalidBankData = new HashMap<>();
        invalidBankData.put("bankName", "");
        invalidBankData.put("referenceCode", "");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Payment("bank-payment-id", order,
                    "TRANSFER_BANK", "SUCCESS", invalidBankData);
        });

        assertEquals("paymentData is not valid", exception.getMessage());
    }
}