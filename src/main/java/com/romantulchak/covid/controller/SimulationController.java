package com.romantulchak.covid.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.covid.model.Simulation;
import com.romantulchak.covid.model.SimulationDetails;
import com.romantulchak.covid.model.Views;
import com.romantulchak.covid.service.SimulationService;
import com.romantulchak.covid.service.impl.SimulationServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 3600L)
@RequestMapping("api/simulation")
public class SimulationController {

    private final SimulationServiceImpl simulationService;

    public SimulationController(SimulationServiceImpl simulationService){
        this.simulationService = simulationService;
    }

    @PostMapping("/startSimulation")
    @JsonView(Views.SimulationFull.class)
    public List<SimulationDetails> startSimulation(@RequestBody Simulation simulation){
        return simulationService.startSimulation(simulation);
    }

    @GetMapping("/results/{page}")
    @JsonView(Views.SimulationFull.class)
    public List<Simulation> results(@PathVariable("page") int page){
        return this.simulationService.results(page);
    }
}
