package com.romantulchak.covid.service.impl;

import com.romantulchak.covid.dto.SimulationDTO;
import com.romantulchak.covid.dto.SimulationDetailsDTO;
import com.romantulchak.covid.model.Simulation;
import com.romantulchak.covid.model.SimulationDetails;
import com.romantulchak.covid.repository.SimulationDetailsRepository;
import com.romantulchak.covid.repository.SimulationRepository;
import com.romantulchak.covid.service.SimulationService;
import com.romantulchak.covid.transformer.Transformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimulationServiceImpl implements SimulationService {

    private final SimulationRepository simulationRepository;
    private final SimulationDetailsRepository simulationDetailsRepository;
    private final Transformer transformer;
    private long newInfected = 0;
    private long numberOfHealthyWithoutImmunity = 0;

    @Transactional
    @Override
    public List<SimulationDetailsDTO> startSimulation(Simulation simulation) {
        if (simulation != null) {
            simulationRepository.save(simulation);
            List<SimulationDetails> simulationDetails = new ArrayList<>();
            long numberOfHealthy = simulation.getPopulation() - simulation.getInitialNumberOfInfected();
            numberOfHealthyWithoutImmunity = numberOfHealthy;
            newInfected = simulation.getInitialNumberOfInfected();
            additionalSecuritySettings(simulation);
            SimulationDetails simulationDetail = new SimulationDetails(0, 0, numberOfHealthy, 0, 0);
            for (int day = 1; day <= simulation.getDaysOfSimulation(); day++) {
                SimulationDetails simulationDetailsToSave = simulate(simulationDetail, day, simulation);
                simulationDetails.add(simulationDetailsToSave);
                if (simulationDetailsToSave.getNumberOfHealthyWithoutImmunity() <= 0) {
                    break;
                }
            }
            simulationDetailsRepository.saveAll(simulationDetails);
            return simulationDetails.stream()
                    .map(transformer::convertToSimulationDetailsDTO)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private void additionalSecuritySettings(Simulation simulation) {
        if (simulation.isMask()) {
            simulation.setPersonInfectPerDay(Math.round(simulation.getPersonInfectPerDay() - (simulation.getPersonInfectPerDay() * 0.15)));
        }
        if (simulation.isDistance()) {
            simulation.setPersonInfectPerDay(Math.round(simulation.getPersonInfectPerDay() - (simulation.getPersonInfectPerDay() * 0.20)));
        }
    }

    private SimulationDetails simulate(SimulationDetails details, int day, Simulation simulation) {
        if (numberOfHealthyWithoutImmunity < simulation.getPersonInfectPerDay()) {
            long currentNumberOfHealthy = simulation.getPersonInfectPerDay() - (simulation.getPersonInfectPerDay() - numberOfHealthyWithoutImmunity);
            newInfected += currentNumberOfHealthy;
            numberOfHealthyWithoutImmunity -= currentNumberOfHealthy;
        } else {
            newInfected += simulation.getPersonInfectPerDay();
            numberOfHealthyWithoutImmunity -= simulation.getPersonInfectPerDay();
        }
        if (!(day % simulation.getDaysToRecovery() == 0 && day % simulation.getDaysToDeath() == 0)) {
            if (day % simulation.getDaysToRecovery() == 0) {
                setRecovery(details, simulation);
            }
            if (day % simulation.getDaysToDeath() == 0) {
                setDeath(details, simulation);
            }
        }
        details = setDetails(details, day, simulation);
        return new SimulationDetails(details);
    }

    private void setRecovery(SimulationDetails details, Simulation simulation) {
        long numberOfInfectedByLastDays = simulation.getPersonInfectPerDay() * simulation.getDaysToRecovery();

        if (details.getNumberOfInfected() < numberOfInfectedByLastDays) {
            numberOfInfectedByLastDays = numberOfInfectedByLastDays - details.getNumberOfInfected();
            details.setNumberOfHealthyWithImmunity(details.getNumberOfHealthyWithImmunity() + numberOfInfectedByLastDays);
            details.setNumberOfInfected(details.getNumberOfInfected() - numberOfInfectedByLastDays);
        } else {
            details.setNumberOfHealthyWithImmunity(details.getNumberOfHealthyWithImmunity() + numberOfInfectedByLastDays);
            details.setNumberOfInfected(details.getNumberOfInfected() - numberOfInfectedByLastDays);
        }
        newInfected -= numberOfInfectedByLastDays;
    }

    private void setDeath(SimulationDetails details, Simulation simulation) {
        if (details.getNumberOfInfected() < simulation.getMortalityRate()) {
            long numberOfInfected = simulation.getMortalityRate() - (simulation.getMortalityRate() - details.getNumberOfInfected());
            details.setNumberOfDeath(details.getNumberOfDeath() + numberOfInfected);
            details.setNumberOfInfected(numberOfInfected);
            newInfected -= numberOfInfected;
        } else {
            details.setNumberOfDeath(details.getNumberOfDeath() + simulation.getMortalityRate());
            details.setNumberOfInfected(details.getNumberOfInfected() - simulation.getMortalityRate());
            newInfected -= simulation.getMortalityRate();
        }
    }

    private SimulationDetails setDetails(SimulationDetails details, int day, Simulation simulation) {
        return details.setNumberOfInfected(newInfected)
                .setNumberOfHealthyWithoutImmunity(numberOfHealthyWithoutImmunity)
                .setDay(day)
                .setSimulation(simulation);
    }

    @Override
    public List<SimulationDTO> results(int page) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<Simulation> simulations = simulationRepository.findAll(pageable);
        return simulations.getContent().stream()
                .map(transformer::convertSimulationToDTO)
                .collect(Collectors.toList());
    }
}
