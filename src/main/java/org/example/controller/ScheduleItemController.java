package org.example.controller;

import org.example.model.ScheduleItem;
import org.example.service.ScheduleItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/schedule-items")
public class ScheduleItemController {
    private final ScheduleItemService scheduleItemService;

    public ScheduleItemController (ScheduleItemService scheduleItemService){
        this.scheduleItemService = scheduleItemService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleItem> updateScheduleItem(
            @PathVariable UUID id,
            @RequestBody ScheduleItem updates){
        ScheduleItem updated = scheduleItemService.updateScheduleItem(id, updates);
        return ResponseEntity.ok(updated);
    }
}
