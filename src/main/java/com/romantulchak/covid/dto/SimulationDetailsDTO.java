package com.romantulchak.covid.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.romantulchak.covid.model.Simulation;
import com.romantulchak.covid.model.Views;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
public class SimulationDetailsDTO {

    @JsonView(Views.SimulationFull.class)
    private long id;

    @JsonView(Views.SimulationFull.class)
    private int day;

    @JsonView(Views.SimulationFull.class)
    private long numberOfInfected;

    @JsonView(Views.SimulationFull.class)
    private long numberOfHealthyWithoutImmunity;

    @JsonView(Views.SimulationFull.class)
    private long numberOfDeath;

    @JsonView(Views.SimulationFull.class)
    private long numberOfHealthyWithImmunity;

    @ManyToOne(cascade = CascadeType.ALL)
    private Simulation simulation;
}
