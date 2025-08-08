package org.example.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "training_plans")
public class TrainingPlan {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String goalRace;
    private LocalDate startDate;
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private PlanStatus status; //ACTIVE, COMPLETED, etc.

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "trainingPlan", cascade = CascadeType.ALL)
    private List<ScheduleItem> schedule;

    public TrainingPlan(){
        this.createdAt = LocalDateTime.now();
        this.status = PlanStatus.ACTIVE;
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

    public String getGoalRace() {
        return goalRace;
    }

    public void setGoalRace(String goalRace) {
        this.goalRace = goalRace;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public PlanStatus getStatus() {
        return status;
    }

    public void setStatus(PlanStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<ScheduleItem> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleItem> schedule) {
        this.schedule = schedule;
    }
}
