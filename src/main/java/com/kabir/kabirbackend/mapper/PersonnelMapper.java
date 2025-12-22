package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.PersonnelDTO;
import com.kabir.kabirbackend.entities.Personnel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PersonnelMapper {

    private ModelMapper modelMapper;

    public PersonnelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Personnel toEntity(PersonnelDTO personnelDTO) {
        return Objects.isNull(personnelDTO) ? null : modelMapper.map(personnelDTO, Personnel.class);
    }

    public PersonnelDTO toDTO(Personnel personnel) {
        return Objects.isNull(personnel) ? null : modelMapper.map(personnel, PersonnelDTO.class);
    }
}
