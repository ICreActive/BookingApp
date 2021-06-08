package com.shkubel.project.models.entity;

import javax.persistence.*;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private Seller seller;
    @ManyToOne
    private User user;
    @ManyToOne
    private Hotel hotel;

    private Boolean paid;


    public Invoice(Long id, Seller seller, User user, Hotel hotel) {
        this.id = id;
        this.seller = seller;
        this.user = user;
        this.hotel = hotel;
    }

    public Invoice() {
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
