package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    String id;

    @Setter
    Order order;

    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, Order order, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;

        if (order == null) {
            throw new IllegalArgumentException("order is null");
        } else {
            this.order = order;
        }

        if (paymentData == null) {
            this.status = PaymentStatus.REJECTED.getValue();
        } else if (!validatePaymentData(paymentData)) {
            this.status = PaymentStatus.REJECTED.getValue();
        } else {
            this.status = PaymentStatus.SUCCESS.getValue();
            this.paymentData = paymentData;
        }
    }

    private boolean validatePaymentData(Map<String, String> paymentData) {
        if (this.method.equals(PaymentMethod.VOUCHER.getValue())) {
            return validateVoucherPayment(paymentData.get("voucherCode"));
        } else if (this.method.equals(PaymentMethod.TRANSFER_BANK.getValue())){
            return validateBankPayment(paymentData);
        } else {
            return false;
        }
    }

    private boolean validateVoucherPayment(String voucherCode) {
        if (voucherCode == null) {
            return false;
        }

        if (voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int numericCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                numericCount++;
            }
        }

        if (numericCount != 8) {
            return false;
        }

        return true;
    }

    private boolean validateBankPayment(Map<String, String> paymentData) {
        String bankName = paymentData.get("bankName");
        String referenceCode = paymentData.get("referenceCode");

        if (bankName == null || referenceCode == null) {
            return false;
        }

        if (bankName.length() == 0 || referenceCode.length() == 0) {
            return false;
        }

        return true;
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("status is not valid");
        }
    }

    public void setMethod(String method) {
        if (PaymentMethod.contains(method)) {
            this.method = method;
        } else {
            throw new IllegalArgumentException("method is not valid");
        }
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (validatePaymentData(paymentData)) {
            this.paymentData = paymentData;
        }
    }
}