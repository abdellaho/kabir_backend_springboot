package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.responses.FactureResponse;
import com.kabir.kabirbackend.dto.FactureDTO;

import java.util.List;

public interface IFactureService {

    public FactureDTO save(FactureResponse factureResponse);
    public FactureDTO findById(Long id);
    public FactureResponse findByIdFactureResponse(Long id);
    public List<FactureDTO> findAll();
    public void delete(Long id);
    public List<FactureDTO> search(FactureDTO absenceDTO);
    int getLastNumFacture(FactureDTO factureDTO);
}
