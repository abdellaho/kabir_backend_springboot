package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.responses.AchatFactureResponse;
import com.kabir.kabirbackend.dto.AchatFactureDTO;

import java.util.List;

public interface IAchatFactureService {

    AchatFactureDTO save(AchatFactureResponse achatFactureResponse);
    AchatFactureDTO findById(Long id);

    AchatFactureResponse findByIdAchatSimpleResponse(Long id);

    List<AchatFactureDTO> findAll();
    void delete(Long id);
    List<AchatFactureDTO> search(AchatFactureDTO achatFactureDTO);

    Integer getLastNumAchatFacture(AchatFactureDTO achatFactureDTO);
}
