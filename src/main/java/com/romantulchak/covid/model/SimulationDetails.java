package com.romantulchak.covid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
public class SimulationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int day;

    private long numberOfInfected;

    private long numberOfHealthyWithoutImmunity;

    private long numberOfDeath;

    private long numberOfHealthyWithImmunity;

    @ManyToOne(cascade = CascadeType.ALL)
    private Simulation simulation;

    public SimulationDetails(){

    }
    public SimulationDetails(SimulationDetails simulationDetails){
        this.id = simulationDetails.getId();
        this.day = simulationDetails.getDay();
        this.numberOfInfected = simulationDetails.getNumberOfInfected();
        this.numberOfHealthyWithoutImmunity = simulationDetails.getNumberOfHealthyWithoutImmunity();
        this.numberOfDeath = simulationDetails.getNumberOfDeath();
        this.numberOfHealthyWithImmunity = simulationDetails.getNumberOfHealthyWithImmunity();
        this.simulation = simulationDetails.getSimulation();
    }
    public SimulationDetails(int day, long numberOfInfected, long numberOfHealthyWithoutImmunity, long numberOfDeath, long numberOfHealthyWithImmunity) {
        this.day = day;
        this.numberOfInfected = numberOfInfected;
        this.numberOfHealthyWithoutImmunity = numberOfHealthyWithoutImmunity;
        this.numberOfDeath = numberOfDeath;
        this.numberOfHealthyWithImmunity = numberOfHealthyWithImmunity;
    }
}
