package com.romantulchak.covid.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.covid.model.Views;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SimulationDTO {

    @JsonView(Views.SimulationFull.class)
    private long id;

    @JsonView(Views.SimulationFull.class)
    private String simulationName;

    @JsonView(Views.SimulationFull.class)
    private long population;

    @JsonView(Views.SimulationFull.class)
    private long initialNumberOfInfected;

    @JsonView(Views.SimulationFull.class)
    private long personInfectPerDay;

    @JsonView(Views.SimulationFull.class)
    private long mortalityRate;

    @JsonView(Views.SimulationFull.class)
    private int daysToRecovery;

    @JsonView(Views.SimulationFull.class)
    private int daysToDeath;

    @JsonView(Views.SimulationFull.class)
    private int daysOfSimulation;

    @JsonView(Views.SimulationFull.class)
    private boolean isMask;

    @JsonView(Views.SimulationFull.class)
    private boolean isDistance;

    @JsonView(Views.SimulationFull.class)
    private List<SimulationDetailsDTO> simulationDetails;

}
