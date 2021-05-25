package com.shkubel.project.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class OrderUser {

    @Id
    @GeneratedValue
    private long id;

    private int numberOfSeats;
    private String klassOfApartment;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDateStart;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDateFinish;

    @ManyToOne (fetch = FetchType.EAGER)
    private User user;

    public OrderUser() {
    }

    public OrderUser(long id, int numberOfSeats, String klassOfApartment, LocalDate localDateStart, LocalDate localDateFinish) {
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

    public String getKlassOfApartment() {
        return klassOfApartment;
    }

    public void setKlassOfApartment(String klassOfApartment) {
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
}
