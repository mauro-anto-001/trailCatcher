package org.example.controller;

import org.example.model.Run;
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

    @Autowired
    public RunController(RunService runService){
        this.runService = runService;
    }

    //create a run
    @PostMapping
    public ResponseEntity<Run> createRun(@RequestBody Run run){
        Run created = runService.createRun(run);
        return ResponseEntity.ok(created);
    }

    //get run by id
    @GetMapping("/{id}")
    public ResponseEntity<Run> getRunById(@PathVariable UUID id){
        Optional<Run> run = runService.getRunById(id);
        return run.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Run>> getRunsByUserId(@PathVariable UUID userId){
        List<Run> runs = runService.getRunByUserId(userId);
        return ResponseEntity.ok(runs);
    }
}
