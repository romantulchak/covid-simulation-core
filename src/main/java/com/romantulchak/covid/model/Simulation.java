package com.romantulchak.covid.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Simulation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String simulationName;

    private long population;

    private long initialNumberOfInfected;

    private long personInfectPerDay;

    private long mortalityRate;

    private int daysToRecovery;

    private int daysToDeath;

    private int daysOfSimulation;

    private boolean isMask;

    private boolean isDistance;

    @OneToMany(mappedBy = "simulation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SimulationDetails> simulationDetails;

    public Simulation(){
    }
}
