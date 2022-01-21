package com.romantulchak.covid.repository;

import com.romantulchak.covid.model.SimulationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimulationDetailsRepository extends JpaRepository<SimulationDetails, Long> {

    List<SimulationDetails> findAllById(long id);

}
