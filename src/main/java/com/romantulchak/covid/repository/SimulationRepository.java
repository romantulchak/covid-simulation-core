package com.romantulchak.covid.repository;

import com.romantulchak.covid.model.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimulationRepository extends JpaRepository<Simulation, Long> {
}
