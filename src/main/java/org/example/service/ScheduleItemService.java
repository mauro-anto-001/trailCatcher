package org.example.service;

import org.example.model.ScheduleItem;
import org.example.repository.ScheduleItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ScheduleItemService {

    private final ScheduleItemRepository scheduleItemRepository;

    @Autowired
    public ScheduleItemService(ScheduleItemRepository scheduleItemRepository){
        this.scheduleItemRepository = scheduleItemRepository;
    }

    public ScheduleItem updateScheduleItem(UUID id, ScheduleItem updates){
        ScheduleItem item = scheduleItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule item not found"));

        item.setType(updates.getType());
        item.setDistancePlanned(updates.getDistancePlanned());
        item.setNotes(updates.getNotes());

        return scheduleItemRepository.save(item);
    }
}
