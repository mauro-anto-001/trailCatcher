package org.example.repository;

import org.example.model.ScheduleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ScheduleItemRepository extends JpaRepository<ScheduleItem, UUID> {
    List<ScheduleItem> findByTrainingPlanId(UUID trainingPlanId);

    List<ScheduleItem> findByTrainingPlanIdAndDateAfter(UUID id, LocalDate date);
}
