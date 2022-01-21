package com.romantulchak.covid.service;

import com.romantulchak.covid.dto.SimulationDTO;
import com.romantulchak.covid.dto.SimulationDetailsDTO;
import com.romantulchak.covid.model.Simulation;

import java.util.List;

public interface SimulationService {

    List<SimulationDetailsDTO> startSimulation(Simulation simulation);

    List<SimulationDTO> results(int page);

}
