package com.shkubel.project.models.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Seller {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String BankAccount;

    public Seller() {
    }

    public Seller(Long id, String name, String bankAccount) {
        this.id = id;
        this.name = name;
        BankAccount = bankAccount;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }
}
