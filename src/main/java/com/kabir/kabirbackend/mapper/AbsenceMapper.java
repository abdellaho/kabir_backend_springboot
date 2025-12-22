package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.AbsenceDTO;
import com.kabir.kabirbackend.entities.Absence;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AbsenceMapper {

    private ModelMapper modelMapper;

    public AbsenceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AbsenceDTO toAbsenceDTO(Absence absence) {
        return Objects.isNull(absence) ? null : modelMapper.map(absence, AbsenceDTO.class);
    }

    public Absence toAbsence(AbsenceDTO absenceDTO) {
        return Objects.isNull(absenceDTO) ? null : modelMapper.map(absenceDTO, Absence.class);
    }


}
