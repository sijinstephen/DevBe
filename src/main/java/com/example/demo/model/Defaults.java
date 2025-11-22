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

    // existing
    private String default_cash_account;

    // 🔹 new fields
    private String default_company_name;
    private String default_customer_name;
    private String default_currency;

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

    public String getDefault_cash_account() {
        return default_cash_account;
    }

    public void setDefault_cash_account(String default_cash_account) {
        this.default_cash_account = default_cash_account;
    }

    // 🔹 getters/setters for new fields

    public String getDefault_company_name() {
        return default_company_name;
    }

    public void setDefault_company_name(String default_company_name) {
        this.default_company_name = default_company_name;
    }

    public String getDefault_customer_name() {
        return default_customer_name;
    }

    public void setDefault_customer_name(String default_customer_name) {
        this.default_customer_name = default_customer_name;
    }

    public String getDefault_currency() {
        return default_currency;
    }

    public void setDefault_currency(String default_currency) {
        this.default_currency = default_currency;
    }
}
