package com.shkubel.project.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private String klassApartment;
    private int numberOfSeats;
    private int price;
    private LocalDate dateStart;
    private LocalDate dateFinish;

    @OneToMany
    private Set<Invoice> invoice;


    public Hotel() {
    }

    public Hotel(String title, String description, String klassApartment, int numberOfSeats) {
        this.title = title;
        this.description = description;
        this.klassApartment = klassApartment;
        this.numberOfSeats = numberOfSeats;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public String getKlassApartment() {
        return klassApartment;
    }

    public void setKlassApartment(String klassApartment) {
        this.klassApartment = klassApartment;
    }

    public int getQuantityRoom() {
        return numberOfSeats;
    }

    public void setQuantityRoom(int quantityRoom) {
        this.numberOfSeats = quantityRoom;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Set<Invoice> getInvoice() {
        return invoice;
    }

    public void setInvoice(Set<Invoice> invoice) {
        this.invoice = invoice;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(LocalDate dateFinish) {
        this.dateFinish = dateFinish;
    }
}
