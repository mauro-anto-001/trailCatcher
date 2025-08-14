package org.example.controller;

import org.example.model.TrainingPlan;
import org.example.model.User;
import org.example.service.CurrentUserService;
import org.example.service.TrainingPlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/training-plans")
public class TrainingPlanController {

    private final TrainingPlanService trainingPlanService;
    private final CurrentUserService currentUser;

    public TrainingPlanController(TrainingPlanService trainingPlanService, CurrentUserService currentUser){
        this.trainingPlanService = trainingPlanService;
        this.currentUser = currentUser;
    }

    @PostMapping
    public ResponseEntity<TrainingPlan> createTrainingPlan(@RequestBody TrainingPlan plan){
        User me = currentUser.requireCurrentUser();
        plan.setUser(me);
        return ResponseEntity.ok(trainingPlanService.createTrainingPlan(plan));
    }

    // NEW: list my plans
    @GetMapping("/me")
    public ResponseEntity<List<TrainingPlan>> getMyPlans(){
        User me = currentUser.requireCurrentUser();
        return ResponseEntity.ok(trainingPlanService.getPlansByUser(me.getId()));
    }

    // existing endpoints (e.g., schedule fetch/adjust) can stay as-is
    @GetMapping("/{planId}/schedule")
    public ResponseEntity<?> getScheduleByPlanId(@PathVariable UUID planId){
        return ResponseEntity.ok(trainingPlanService.getScheduleByPlanId(planId));
    }

    @PostMapping("/{planId}/adjust")
    public ResponseEntity<String> adjustPlan(@PathVariable UUID planId, @RequestParam String fromDate){
        trainingPlanService.shiftFutureSchedule(planId, java.time.LocalDate.parse(fromDate));
        return ResponseEntity.ok("Schedule adjusted from " + fromDate);
    }
}
