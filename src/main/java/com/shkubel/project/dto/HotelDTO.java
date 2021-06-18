package com.shkubel.project.dto;

import com.shkubel.project.models.entity.KlassAppartament;

public class HotelDTO {

    private String title;

    private String description;

    private KlassAppartament klassApartment;

    private Integer numberOfSeats;

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

    public KlassAppartament getKlassApartment() {
        return klassApartment;
    }

    public void setKlassApartment(KlassAppartament klassApartment) {
        this.klassApartment = klassApartment;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
