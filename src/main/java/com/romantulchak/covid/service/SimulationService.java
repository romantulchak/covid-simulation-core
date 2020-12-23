package com.romantulchak.covid.service;

import com.romantulchak.covid.model.Simulation;
import com.romantulchak.covid.model.SimulationDetails;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SimulationService {

    List<SimulationDetails> startSimulation(Simulation simulation);
    List<Simulation> results(int page);


}
