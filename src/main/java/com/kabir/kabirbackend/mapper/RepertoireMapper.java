package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.RepertoireDTO;
import com.kabir.kabirbackend.entities.Repertoire;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RepertoireMapper {

    private ModelMapper modelMapper;

    public RepertoireMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public RepertoireDTO toRepertoireDTO(Repertoire repertoire) {
        return Objects.isNull(repertoire) ? null : modelMapper.map(repertoire, RepertoireDTO.class);
    }

    public Repertoire toRepertoire(RepertoireDTO repertoireDTO) {
        return Objects.isNull(repertoireDTO) ? null : modelMapper.map(repertoireDTO, Repertoire.class);
    }

}
