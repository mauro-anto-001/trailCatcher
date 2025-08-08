package org.example.controller;

import org.example.model.ScheduleItem;
import org.example.model.TrainingPlan;
import org.example.service.TrainingPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/training-plans")
public class TrainingPlanController {
    private final TrainingPlanService trainingPlanService;
    @Autowired
    public TrainingPlanController(TrainingPlanService trainingPlanService){
        this.trainingPlanService = trainingPlanService;
    }

    @PostMapping
    public ResponseEntity<TrainingPlan> createTrainingPlan(@RequestBody TrainingPlan plan){
        TrainingPlan created = trainingPlanService.createTrainingPlan(plan);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrainingPlan>> getTrainingPlanByUserId(@PathVariable UUID userId){
        List<TrainingPlan> plans = trainingPlanService.getPlansByUser(userId);
        return ResponseEntity.ok(plans);
    }

    @GetMapping("/{planId}/schedule")
    public ResponseEntity<List<ScheduleItem>> getScheduleByPlanId(@PathVariable UUID planId) {
        List<ScheduleItem> schedule = trainingPlanService.getScheduleByPlanId(planId);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping("/{planId}/adjust")
    public ResponseEntity<String> adjustPlan(
            @PathVariable UUID planId,
            @RequestParam("fromDate") String fromDateStr) {
        LocalDate fromDate = LocalDate.parse(fromDateStr);
        trainingPlanService.shiftFutureSchedule(planId,fromDate);

        return ResponseEntity.ok("Schedule adjusted from " + fromDate);
    }
}
