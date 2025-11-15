package com.example.demo.api.dto;

import java.math.BigDecimal;

public class InvoicePayment {
    public Long inv_id;
    public BigDecimal amount;
    public String mode; // BANK | CASH
    public Long bank_ledger_id; // nullable
    public String date;         // yyyy-MM-dd, nullable
    public String reference;    // nullable
    public String notes;        // nullable
}
