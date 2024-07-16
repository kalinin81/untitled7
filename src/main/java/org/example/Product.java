package org.example;

import java.math.BigDecimal;

public class Product {

    private Long id;
    private String account;
    private BigDecimal balance;
    private Type type;
    private Long userId;

    public Product() {
    }

    public Product(Long id, String account, BigDecimal balance, Type type, Long userId) {
        this.id = id;
        this.account = account;
        this.balance = balance;
        this.type = type;
        this.userId = userId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Type getType() {
        return type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
