package com.romantulchak.covid.service.impl;

import com.romantulchak.covid.model.Simulation;
import com.romantulchak.covid.model.SimulationDetails;
import com.romantulchak.covid.repository.SimulationDetailsRepository;
import com.romantulchak.covid.repository.SimulationRepository;
import com.romantulchak.covid.service.SimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            additionalSecuritySettings(simulation);
            SimulationDetails simulationDetail = new SimulationDetails(0, 0, numberOfHealthy, 0, 0);
            for (int day = 1; day <= simulation.getDaysOfSimulation(); day++){
                 SimulationDetails simulationDetailsToSave = simulate(simulationDetail, day, simulation);
                 simulationDetails.add(simulationDetailsToSave);
                if(simulationDetailsToSave.getNumberOfHealthyWithoutImmunity() <= 0) {
                    break;
                }
            }
            simulationDetailsRepository.saveAll(simulationDetails);
            return simulationDetails;
        }
        return new ArrayList<>();
    }

    private void additionalSecuritySettings(Simulation simulation) {
        if(simulation.isMask()){
            simulation.setPersonInfectPerDay(Math.round(simulation.getPersonInfectPerDay() - (simulation.getPersonInfectPerDay() * 0.15)));
        }
        if(simulation.isDistance()){
            simulation.setPersonInfectPerDay(Math.round(simulation.getPersonInfectPerDay() - (simulation.getPersonInfectPerDay() * 0.20)));
        }
    }

    private SimulationDetails simulate(SimulationDetails details,int day, Simulation simulation){
        if(numberOfHealthyWithoutImmunity < simulation.getPersonInfectPerDay()){
            long currentNumberOfHealthy = simulation.getPersonInfectPerDay() - (simulation.getPersonInfectPerDay() - numberOfHealthyWithoutImmunity);
            newInfected += currentNumberOfHealthy;
            numberOfHealthyWithoutImmunity -= currentNumberOfHealthy;
        }else {
            newInfected += simulation.getPersonInfectPerDay();
            numberOfHealthyWithoutImmunity -= simulation.getPersonInfectPerDay();
        }
        if(!(day % simulation.getDaysToRecovery() == 0 && day % simulation.getDaysToDeath() == 0)){
            if (day % simulation.getDaysToRecovery() == 0){
                setRecovery(details, simulation);
            }
            if(day % simulation.getDaysToDeath() == 0){
                setDeath(details, simulation);
            }
        }
        setDetails(details, day, simulation);
        return new SimulationDetails(details);
    }

    private void setRecovery(SimulationDetails details, Simulation simulation) {
        long numberOfInfectedByLastDays = simulation.getPersonInfectPerDay() * simulation.getDaysToRecovery();

        if(details.getNumberOfInfected() < numberOfInfectedByLastDays){
            numberOfInfectedByLastDays = numberOfInfectedByLastDays - details.getNumberOfInfected();
            details.setNumberOfHealthyWithImmunity(details.getNumberOfHealthyWithImmunity() + numberOfInfectedByLastDays);
            details.setNumberOfInfected(details.getNumberOfInfected() - numberOfInfectedByLastDays);
        }else {
            details.setNumberOfHealthyWithImmunity(details.getNumberOfHealthyWithImmunity() + numberOfInfectedByLastDays);
            details.setNumberOfInfected(details.getNumberOfInfected() - numberOfInfectedByLastDays);
        }
        newInfected -= numberOfInfectedByLastDays;
    }

    private void setDeath(SimulationDetails details, Simulation simulation) {
        if(details.getNumberOfInfected() < simulation.getMortalityRate()){
            long numberOfInfected = simulation.getMortalityRate() - (simulation.getMortalityRate() - details.getNumberOfInfected());
            details.setNumberOfDeath(details.getNumberOfDeath() + numberOfInfected);
            details.setNumberOfInfected(numberOfInfected);
            newInfected -= numberOfInfected;
        }else {
            details.setNumberOfDeath(details.getNumberOfDeath() + simulation.getMortalityRate());
            details.setNumberOfInfected(details.getNumberOfInfected() - simulation.getMortalityRate());
            newInfected -= simulation.getMortalityRate();
        }
    }

    private void setDetails(SimulationDetails details, int day, Simulation simulation) {
        details.setNumberOfInfected(newInfected);
        details.setNumberOfHealthyWithoutImmunity(numberOfHealthyWithoutImmunity);
        details.setDay(day);
        details.setSimulation(simulation);
    }

    @Override
    public List<Simulation> results(int page) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<Simulation> simulations = simulationRepository.findAll(pageable);
        return new ArrayList<>(simulations.getContent());
    }
}
