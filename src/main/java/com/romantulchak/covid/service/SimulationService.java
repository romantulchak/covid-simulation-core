package com.romantulchak.covid.service;

import com.romantulchak.covid.model.Simulation;
import com.romantulchak.covid.model.SimulationDetails;

import java.util.List;

public interface SimulationService {

    List<SimulationDetails> startSimulation(Simulation simulation);
    List<SimulationDetails> results();


}
