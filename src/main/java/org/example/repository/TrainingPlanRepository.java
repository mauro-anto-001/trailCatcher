package org.example.repository;

import org.example.model.TrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, UUID> {
    List<TrainingPlan> findByUserId(UUID userId); //show all plans for a user
}
