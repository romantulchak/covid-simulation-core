package com.romantulchak.covid.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.covid.dto.SimulationDTO;
import com.romantulchak.covid.dto.SimulationDetailsDTO;
import com.romantulchak.covid.model.Simulation;
import com.romantulchak.covid.model.Views;
import com.romantulchak.covid.service.impl.SimulationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("/api/simulation")
@RequiredArgsConstructor
public class SimulationController {

    private final SimulationServiceImpl simulationService;

    @PostMapping("/start")
    @JsonView(Views.SimulationFull.class)
    public List<SimulationDetailsDTO> startSimulation(@RequestBody Simulation simulation){
        return simulationService.startSimulation(simulation);
    }

    @GetMapping("/results/{page}")
    @JsonView(Views.SimulationFull.class)
    public List<SimulationDTO> results(@PathVariable("page") int page){
        return this.simulationService.results(page);
    }
}
