package org.example.repository;

import org.example.model.Run;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RunRepository extends JpaRepository<Run, UUID> {
    List<Run> findByUserId(UUID userId);
}
