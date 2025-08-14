package org.example.controller;

import org.example.dto.CreateRunRequest;
import org.example.model.Run;
import org.example.model.User;
import org.example.service.CurrentUserService;
import org.example.service.RunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController //registers this class as REST controller that returns JSON
@RequestMapping("/runs") //base route
public class RunController {
    private final RunService runService;
    private final CurrentUserService currentUser;
    @Autowired
    public RunController(RunService runService, CurrentUserService currentUser){

        this.runService = runService;
        this.currentUser = currentUser;
    }

    //create a run
    @PostMapping
    public ResponseEntity<Run> createRun(@RequestBody @jakarta.validation.Valid CreateRunRequest req){
        User me = currentUser.requireCurrentUser();
        Run run = new Run();
        run.setUser(me);
        run.setDate(req.date != null ? req.date : java.time.LocalDateTime.now());
        run.setDurationInSeconds(req.durationInSeconds);
        run.setDistance(req.distance);
        run.setNotes(req.notes);
        run.setShoeUsed(req.shoeUsed);
        run.setWeather(req.weather);
        run.setGpsFileUrl(req.gpsFileUrl);
        // compute average pace (seconds per unit)
        run.setAveragePace(req.distance > 0 ? (req.durationInSeconds / req.distance) : 0f);
        return ResponseEntity.ok(runService.createRun(run));
    }


    //get run by id
    @GetMapping("/{id}")
    public ResponseEntity<Run> getRunById(@PathVariable UUID id){
        Optional<Run> run = runService.getRunById(id);
        return run.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<List<Run>> getMyRuns(){
        User me = currentUser.requireCurrentUser();
        return ResponseEntity.ok(runService.getRunByUserId(me.getId()));
    }
}
