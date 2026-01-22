package com.kabir.kabirbackend.mapper;


import com.kabir.kabirbackend.dto.ChequeDTO;
import com.kabir.kabirbackend.entities.Cheque;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ChequeMapper {

    private final ModelMapper modelMapper;

    public ChequeMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ChequeDTO toDto(Cheque cheque) {
        return modelMapper.map(cheque, ChequeDTO.class);
    }

    public Cheque toEntity(ChequeDTO chequeDTO) {
        return modelMapper.map(chequeDTO, Cheque.class);
    }
}
