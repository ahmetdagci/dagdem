package com.tr.cay.dagdem.wsmodel;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerTrafficReport implements Serializable {

    private String customerNo;
    private BigDecimal totalPaymentAmount;
    private BigDecimal totalSaleAmount;

    public CustomerTrafficReport() {

    }

    public CustomerTrafficReport(String customerNo, BigDecimal totalPaymentAmount, BigDecimal totalSaleAmount) {
        this.customerNo = customerNo;
        this.totalPaymentAmount = totalPaymentAmount;
        this.totalSaleAmount = totalSaleAmount;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public BigDecimal getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public void setTotalPaymentAmount(BigDecimal totalPaymentAmount) {
        this.totalPaymentAmount = totalPaymentAmount;
    }

    public BigDecimal getTotalSaleAmount() {
        return totalSaleAmount;
    }

    public void setTotalSaleAmount(BigDecimal totalSaleAmount) {
        this.totalSaleAmount = totalSaleAmount;
    }


}
