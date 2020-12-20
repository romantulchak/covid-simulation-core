package com.romantulchak.covid.service.impl;

import com.romantulchak.covid.model.Simulation;
import com.romantulchak.covid.model.SimulationDetails;
import com.romantulchak.covid.repository.SimulationDetailsRepository;
import com.romantulchak.covid.repository.SimulationRepository;
import com.romantulchak.covid.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationServiceImpl implements SimulationService {


    private final SimulationRepository simulationRepository;
    private final SimulationDetailsRepository simulationDetailsRepository;
    private long newInfected = 0;
    private long numberOfHealthyWithoutImmunity = 0;

    @Autowired
    public SimulationServiceImpl(SimulationRepository simulationRepository, SimulationDetailsRepository simulationDetailsRepository){
        this.simulationRepository = simulationRepository;
        this.simulationDetailsRepository = simulationDetailsRepository;
    }

    @Override
    public List<SimulationDetails> startSimulation(Simulation simulation) {
        if(simulation != null){
            simulationRepository.save(simulation);
            List<SimulationDetails> simulationDetails = new ArrayList<>();
            long numberOfHealthy = simulation.getPopulation() - simulation.getInitialNumberOfInfected();
            numberOfHealthyWithoutImmunity = numberOfHealthy;
            newInfected = simulation.getInitialNumberOfInfected();
            SimulationDetails simulationDetail = new SimulationDetails(0, 0, numberOfHealthy, 0, 0);
            for (int day = 1; day <= simulation.getDaysOfSimulation(); day++){
                 SimulationDetails simulationDetailsToSave = simulate(simulationDetail, day, simulation);
                 simulationDetails.add(simulationDetailsToSave);
                if(simulationDetailsToSave.getNumberOfInfected() <= 0 || simulationDetailsToSave.getNumberOfHealthyWithoutImmunity() <= 0){
                    simulationDetailsToSave.setNumberOfHealthyWithImmunity(simulationDetailsToSave.getNumberOfHealthyWithImmunity() + simulationDetailsToSave.getNumberOfInfected());
                    simulationDetailsToSave.setNumberOfInfected(0);
                    break;
                }
            }
            simulationDetailsRepository.saveAll(simulationDetails);
            return simulationDetails;
        }
        return new ArrayList<>();
    }
    private SimulationDetails simulate(SimulationDetails details,int day, Simulation simulation){
        newInfected += simulation.getPersonInfectPerDay();
        numberOfHealthyWithoutImmunity -= simulation.getPersonInfectPerDay();
        setDetails(details, day, simulation);
        if (day % simulation.getDaysToRecovery() == 0){
            long numberOfInfectedByLastDays = simulation.getPersonInfectPerDay() * simulation.getDaysToRecovery();
            details.setNumberOfHealthyWithImmunity(details.getNumberOfHealthyWithImmunity() +numberOfInfectedByLastDays);
            details.setNumberOfInfected(details.getNumberOfInfected() - numberOfInfectedByLastDays);
            newInfected -= numberOfInfectedByLastDays;
        }
        if(day % simulation.getDaysToDeath() == 0){
            details.setNumberOfDeath(details.getNumberOfDeath() + simulation.getMortalityRate());
            details.setNumberOfInfected(details.getNumberOfInfected() - simulation.getMortalityRate());
            newInfected -= simulation.getMortalityRate();
        }
        return new SimulationDetails(details);
    }

    private void setDetails(SimulationDetails details, int day, Simulation simulation) {
        details.setNumberOfInfected(newInfected);
        details.setNumberOfHealthyWithoutImmunity(numberOfHealthyWithoutImmunity);
        details.setDay(day);
        details.setSimulation(simulation);
    }

    @Override
    public List<SimulationDetails> results() {
        return new ArrayList<>();
    }
}
