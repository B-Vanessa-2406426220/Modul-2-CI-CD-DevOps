package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Map;

@Getter
public class Payment {
    String id;

    @Setter
    Order order;

    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, Order order, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = status;

        if (order == null) {
            throw new IllegalArgumentException("order is null");
        } else {
            this.order = order;
        }

        if (paymentData == null) {
            throw new IllegalArgumentException("paymentData is null");
        } else if (!validatePaymentData(paymentData)) {
            throw new IllegalArgumentException("paymentData is not valid");
        } else {
            this.paymentData = paymentData;
        }
    }

    private boolean validatePaymentData(Map<String, String> paymentData) {
        String[] methodList = {"VOUCHER", "TRANSFER_BANK"};
        if (this.method.equals(methodList[0])) {
            return validateVoucherPayment(paymentData.get("voucherCode"));
        } else if (this.method.equals(methodList[1])) {
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
        String[] statusList = {"SUCCESS", "REJECTED"};
        if (Arrays.asList(statusList).contains(status)) {
            this.status = status;
        }
    }

    public void setMethod(String method) {
        String[] methodList = {"VOUCHER", "BANK_TRANSFER"};
        if (Arrays.asList(methodList).contains(method)) {
            this.method = method;
        }
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (validatePaymentData(paymentData)) {
            this.paymentData = paymentData;
        }
    }
}