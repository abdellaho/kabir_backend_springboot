package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.responses.AchatEtrangerResponse;
import com.kabir.kabirbackend.dto.AchatEtrangerDTO;

import java.util.List;

public interface IAchatEtrangerService {
    AchatEtrangerDTO save(AchatEtrangerResponse achatEtrangerResponse);

    void delete(Long id);

    AchatEtrangerResponse findByIdAchatEtrangerResponse(Long id);

    List<AchatEtrangerDTO> findAll();

    AchatEtrangerDTO findById(Long id);

    List<AchatEtrangerDTO> search(AchatEtrangerDTO achatEtrangerDTO);

    Integer getLastNumAchatEtranger(AchatEtrangerDTO achatEtrangerDTO);

    boolean exist(AchatEtrangerDTO achatEtrangerDTO);
}
