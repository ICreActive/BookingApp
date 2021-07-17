package com.shkubel.project.models.entity;

import javax.persistence.*;

@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String BankAccount;
    private String address;

    @Column(name = "is_active")
    private Boolean isActive;

    private String creatingDate;
    private String updatingDate;

    public Seller() {
    }

    public Seller(Long id, String name, String bankAccount) {
        this.id = id;
        this.name = name;
        BankAccount = bankAccount;
    }

    public Long getId() {
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

    public String getCreatingDate() {
        return creatingDate;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

    public String getUpdatingDate() {
        return updatingDate;
    }

    public void setUpdatingDate(String updatingDate) {
        this.updatingDate = updatingDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
