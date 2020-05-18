package com.hsbc.it.currencycount.vo;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * pojo
 */
public class CurrencyAmount {
    private String currency;
    private BigDecimal amount;
    private BigDecimal usd;

    public CurrencyAmount(String currency, BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUsd() {
        return usd;
    }

    public void setUsd(BigDecimal usd) {
        this.usd = usd;
    }
}
