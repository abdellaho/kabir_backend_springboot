package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.EmployeDTO;
import com.kabir.kabirbackend.entities.Employe;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeMapper {

    private final ModelMapper modelMapper;

    public EmployeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Employe toEmploye(EmployeDTO employeDTO) {
        return modelMapper.map(employeDTO, Employe.class);
    }

    public EmployeDTO toEmployeDTO(Employe employe) {
        return modelMapper.map(employe, EmployeDTO.class);
    }
}
