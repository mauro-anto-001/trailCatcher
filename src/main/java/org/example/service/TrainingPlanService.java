package org.example.service;

import org.example.model.ScheduleItem;
import org.example.model.TrainingPlan;
import org.example.model.WorkoutType;
import org.example.repository.ScheduleItemRepository;
import org.example.repository.TrainingPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TrainingPlanService {
    private List<ScheduleItem> generate7DaySchedule(TrainingPlan plan){
        List<ScheduleItem> schedule = new ArrayList<>();
        LocalDate date = plan.getStartDate();

        WorkoutType[] workoutCycle = {
                WorkoutType.EASY,
                WorkoutType.REST,
                WorkoutType.INTERVAL,
                WorkoutType.REST,
                WorkoutType.REST,
                WorkoutType.LONG,
                WorkoutType.REST
        };

        for(int i = 0; i < 7; i++) {
            ScheduleItem item = new ScheduleItem();
            item.setTrainingPlan(plan);
            item.setDate(date.plusDays(i));
            item.setType(workoutCycle[i]);
            item.setDistancePlanned(workoutCycle[i] == WorkoutType.REST ? 0 : 3+i);
            item.setNotes("Auto-generated");
            schedule.add(item);
        }

        return schedule;
    }
    private TrainingPlanRepository trainingPlanRepository;
    private ScheduleItemRepository scheduleItemRepository;
    @Autowired
    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository,
                               ScheduleItemRepository scheduleItemRepository){
        this.trainingPlanRepository = trainingPlanRepository;
        this.scheduleItemRepository = scheduleItemRepository;
    }

    public TrainingPlan createTrainingPlan(TrainingPlan plan){
        TrainingPlan savedPlan = trainingPlanRepository.save(plan);

        List<ScheduleItem> schedule = generate7DaySchedule(savedPlan);
        scheduleItemRepository.saveAll(schedule);
        return savedPlan;
    };
    public List<TrainingPlan> getPlansByUser(UUID userId){
        return trainingPlanRepository.findByUserId(userId);
    };

    public List<ScheduleItem> getScheduleByPlanId(UUID planId) {
        return scheduleItemRepository.findByTrainingPlanId(planId);
    }

    public void shiftFutureSchedule(UUID planId, LocalDate fromDate){
        List<ScheduleItem> plan = scheduleItemRepository.findByTrainingPlanIdAndDateAfter(planId, fromDate);
        for(ScheduleItem item : plan){
            item.setDate(item.getDate().plusDays(1));
        }
        scheduleItemRepository.saveAll(plan);
    }

}
