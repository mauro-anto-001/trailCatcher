package org.example.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class CreateRunRequest {
    public LocalDateTime date;
    @Positive(message = "Duration must be > 0 seconds")
    public int durationInSeconds;
    @Positive(message = "Distance must be > 0")
    public float distance;
    @Size(max = 500, message = "Notes too long")
    public String notes;
    public String shoeUsed;
    public String weather;
    public String gpsFileUrl;

}
