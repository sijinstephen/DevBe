package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "defaults")
public class Defaults {

    @Id
    private String default_credit_account;

    private String default_debit_account;
    private String default_income_account;
    private String default_expense_account;

    // ✅ New field: default cash account
    private String default_cash_account;

    public Defaults() {
    }

    public String getDefault_credit_account() {
        return default_credit_account;
    }

    public void setDefault_credit_account(String default_credit_account) {
        this.default_credit_account = default_credit_account;
    }

    public String getDefault_debit_account() {
        return default_debit_account;
    }

    public void setDefault_debit_account(String default_debit_account) {
        this.default_debit_account = default_debit_account;
    }

    public String getDefault_income_account() {
        return default_income_account;
    }

    public void setDefault_income_account(String default_income_account) {
        this.default_income_account = default_income_account;
    }

    public String getDefault_expense_account() {
        return default_expense_account;
    }

    public void setDefault_expense_account(String default_expense_account) {
        this.default_expense_account = default_expense_account;
    }

    // ✅ Getter/setter for new cash account field
    public String getDefault_cash_account() {
        return default_cash_account;
    }

    public void setDefault_cash_account(String default_cash_account) {
        this.default_cash_account = default_cash_account;
    }
}
