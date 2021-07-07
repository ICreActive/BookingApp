package com.shkubel.project.models.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class OrderUser {

    @Id
    @GeneratedValue
    private long id;

    private int numberOfSeats;

    @Value("Unknown")
    private KlassAppartament klassOfApartment;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDateStart;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDateFinish;

    @ManyToOne (fetch = FetchType.EAGER)
    private User user;

    private String creatingDate;
    private String updatingDate;

    public OrderUser() {
    }

    public OrderUser(long id, int numberOfSeats, KlassAppartament klassOfApartment, LocalDate localDateStart, LocalDate localDateFinish) {
        this.id = id;
        this.numberOfSeats = numberOfSeats;
        this.klassOfApartment = klassOfApartment;
        this.localDateStart = localDateStart;
        this.localDateFinish = localDateFinish;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public KlassAppartament getKlassOfApartment() {
        return klassOfApartment;
    }

    public void setKlassOfApartment(KlassAppartament klassOfApartment) {
        this.klassOfApartment = klassOfApartment;
    }

    public LocalDate getLocalDateStart() {
        return localDateStart;
    }

    public void setLocalDateStart(LocalDate localDateStart) {
        this.localDateStart = localDateStart;
    }

    public LocalDate getLocalDateFinish() {
        return localDateFinish;
    }

    public void setLocalDateFinish(LocalDate localDateFinish) {
        this.localDateFinish = localDateFinish;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
