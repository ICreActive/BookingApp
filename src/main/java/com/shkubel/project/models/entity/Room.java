package com.shkubel.project.models.entity;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String title;

    private String description;

    @Value("Unknown")
    private KlassAppartament klassApartment;

    @Column(columnDefinition = "integer default 0")
    private Integer numberOfSeats;
    @Column(columnDefinition = "integer default 0")
    private Integer price;


    private String filename;

    private Boolean isAvailable;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "room")
    private Set<Invoice> invoices;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public KlassAppartament getKlassApartment() {
        return klassApartment;
    }

    public void setKlassApartment(KlassAppartament klassApartment) {
        this.klassApartment = klassApartment;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Set<Invoice> getInvoice() {
        return invoices;
    }

    public void setInvoice(Set<Invoice> invoice) {
        this.invoices = invoice;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        this.isAvailable = available;
    }


    public boolean isAvailableOnDate(LocalDate dateCheckIn, LocalDate dateCheckOut) {

        for (Invoice invoice : invoices) {

            if (invoice.isActive()) {

                LocalDate invCheckIn = invoice.getOrderUser().getLocalDateStart();
                LocalDate invCheckOut = invoice.getOrderUser().getLocalDateFinish();
                if (invCheckIn.equals(dateCheckIn) ||
                        invCheckIn.isAfter(dateCheckIn)
                                && invCheckIn.isBefore(dateCheckOut) ||
                        invCheckIn.isBefore(dateCheckIn) && invCheckOut.isAfter(dateCheckOut) ||
                        invCheckIn.isBefore(dateCheckIn) && invCheckOut.isAfter(dateCheckIn) && invCheckOut.isBefore(dateCheckOut)
                ) {
                    return false;
                }
            }
        }
        return true;
    }
}



