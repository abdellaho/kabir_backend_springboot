package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.PrimeDTO;
import com.kabir.kabirbackend.entities.Prime;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PrimeMapper {

    private ModelMapper modelMapper;

    public PrimeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PrimeDTO toPrimeDTO(Prime prime) {
        return Objects.isNull(prime) ? null : modelMapper.map(prime, PrimeDTO.class);
    }

    public Prime toPrime(PrimeDTO primeDTO) {
        return Objects.isNull(primeDTO) ? null : modelMapper.map(primeDTO, Prime.class);
    }
}
