package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "runs")
public class Run {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime date;

    private int durationInSeconds;
    private float distance;
    private float averagePace;

    private String notes;
    private String gpsFileUrl;
    private String routeDtaJson;
    private String shoeUsed;
    private String weather;

    public Run() {
        this.date = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getAveragePace() {
        return averagePace;
    }

    public void setAveragePace(float averagePace) {
        this.averagePace = averagePace;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGpsFileUrl() {
        return gpsFileUrl;
    }

    public void setGpsFileUrl(String gpsFileUrl) {
        this.gpsFileUrl = gpsFileUrl;
    }

    public String getRouteDtaJson() {
        return routeDtaJson;
    }

    public void setRouteDtaJson(String routeDtaJson) {
        this.routeDtaJson = routeDtaJson;
    }

    public String getShoeUsed() {
        return shoeUsed;
    }

    public void setShoeUsed(String shoeUsed) {
        this.shoeUsed = shoeUsed;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
