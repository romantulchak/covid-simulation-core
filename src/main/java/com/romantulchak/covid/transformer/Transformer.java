package com.romantulchak.covid.transformer;

import com.romantulchak.covid.dto.SimulationDTO;
import com.romantulchak.covid.dto.SimulationDetailsDTO;
import com.romantulchak.covid.model.Simulation;
import com.romantulchak.covid.model.SimulationDetails;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Transformer {

    private final ModelMapper modelMapper;

    public SimulationDTO convertSimulationToDTO(Simulation simulation){
        return modelMapper.map(simulation, SimulationDTO.class);
    }

    public SimulationDetailsDTO convertToSimulationDetailsDTO(SimulationDetails simulationDetails){
        return modelMapper.map(simulationDetails, SimulationDetailsDTO.class);
    }
}
