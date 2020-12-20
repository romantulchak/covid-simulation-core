package com.romantulchak.covid.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;

@Entity
public class SimulationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public long getNumberOfInfected() {
        return numberOfInfected;
    }

    public void setNumberOfInfected(long numberOfInfected) {
        this.numberOfInfected = numberOfInfected;
    }

    public long getNumberOfHealthyWithoutImmunity() {
        return numberOfHealthyWithoutImmunity;
    }

    public void setNumberOfHealthyWithoutImmunity(long numberOfHealthyWithoutImmunity) {
        this.numberOfHealthyWithoutImmunity = numberOfHealthyWithoutImmunity;
    }

    public long getNumberOfDeath() {
        return numberOfDeath;
    }

    public void setNumberOfDeath(long numberOfDeath) {
        this.numberOfDeath = numberOfDeath;
    }

    public long getNumberOfHealthyWithImmunity() {
        return numberOfHealthyWithImmunity;
    }

    public void setNumberOfHealthyWithImmunity(long numberOfHealthyWithImmunity) {
        this.numberOfHealthyWithImmunity = numberOfHealthyWithImmunity;
    }

    public Simulation getSimulation() {
        return simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
}
