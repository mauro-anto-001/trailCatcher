package org.example.service;

import org.example.model.Run;
import org.example.repository.RunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RunService {
    private final RunRepository runRepository;

    @Autowired
    public RunService(RunRepository runRepository) {

        this.runRepository = runRepository;
    }

    public Run createRun(Run run){
        return runRepository.save(run);
    }

    public Optional<Run> getRunById(UUID id) {
        return runRepository.findById(id);
    }

    public List<Run> getRunByUserId(UUID id){
        return runRepository.findByUserId(id);
    }
}
