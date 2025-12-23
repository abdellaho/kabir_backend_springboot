package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.FactureDTO;

import java.util.List;

public interface IFactureService {

    public FactureDTO save(FactureDTO absenceDTO);
    public FactureDTO findById(Long id);
    public List<FactureDTO> findAll();
    public void delete(Long id);
    public List<FactureDTO> search(FactureDTO absenceDTO);
}
