package com.romantulchak.covid.model;



import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

@Entity
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "simulation", cascade = CascadeType.ALL)
    @JsonView(Views.SimulationFull.class)
    private List<SimulationDetails> simulationDetails;

    public Simulation(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public void setSimulationName(String simulationName) {
        this.simulationName = simulationName;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getInitialNumberOfInfected() {
        return initialNumberOfInfected;
    }

    public void setInitialNumberOfInfected(long initialNumberOfInfected) {
        this.initialNumberOfInfected = initialNumberOfInfected;
    }

    public long getPersonInfectPerDay() {
        return personInfectPerDay;
    }

    public void setPersonInfectPerDay(long personInfectPerDay) {
        this.personInfectPerDay = personInfectPerDay;
    }

    public long getMortalityRate() {
        return mortalityRate;
    }

    public void setMortalityRate(long mortalityRate) {
        this.mortalityRate = mortalityRate;
    }

    public int getDaysToRecovery() {
        return daysToRecovery;
    }

    public void setDaysToRecovery(int daysToRecovery) {
        this.daysToRecovery = daysToRecovery;
    }

    public int getDaysToDeath() {
        return daysToDeath;
    }

    public void setDaysToDeath(int daysToDeath) {
        this.daysToDeath = daysToDeath;
    }

    public int getDaysOfSimulation() {
        return daysOfSimulation;
    }

    public void setDaysOfSimulation(int daysOfSimulation) {
        this.daysOfSimulation = daysOfSimulation;
    }

    public List<SimulationDetails> getSimulationDetails() {
        return simulationDetails;
    }

    public void setSimulationDetails(List<SimulationDetails> simulationDetails) {
        this.simulationDetails = simulationDetails;
    }

    public boolean isMask() {
        return isMask;
    }

    public void setMask(boolean mask) {
        isMask = mask;
    }

    public boolean isDistance() {
        return isDistance;
    }

    public void setDistance(boolean distance) {
        isDistance = distance;
    }
}
