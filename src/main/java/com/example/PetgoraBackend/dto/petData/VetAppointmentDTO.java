package com.example.PetgoraBackend.dto.petData;

import java.time.LocalDate;

public class VetAppointmentDTO {

    private LocalDate appointmentDate;
    private double weight;
    private double body;
    private double tail;
    private double chest;

    // Getters and setters
    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBody() {
        return body;
    }

    public void setBody(double body) {
        this.body = body;
    }

    public double getTail() {
        return tail;
    }

    public void setTail(double tail) {
        this.tail = tail;
    }

    public double getChest() {
        return chest;
    }

    public void setChest(double chest) {
        this.chest = chest;
    }
}
