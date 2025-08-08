package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "scheduled_items")
public class ScheduleItem {
    @Id
    @GeneratedValue
    private UUID id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "training_plan_id")
    private TrainingPlan trainingPlan;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private WorkoutType type; //easy long, rest, etc

    private float distancePlanned;
    private String notes;

    @OneToOne
    @JoinColumn(name = "completed_run_id")
    private Run completedRun;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public WorkoutType getType() {
        return type;
    }

    public void setType(WorkoutType type) {
        this.type = type;
    }

    public float getDistancePlanned() {
        return distancePlanned;
    }

    public void setDistancePlanned(float distancePlanned) {
        this.distancePlanned = distancePlanned;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Run getCompletedRun() {
        return completedRun;
    }

    public void setCompletedRun(Run completedRun) {
        this.completedRun = completedRun;
    }
}
