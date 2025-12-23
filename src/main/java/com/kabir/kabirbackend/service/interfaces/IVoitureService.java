package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.VoitureDTO;

import java.util.List;

public interface IVoitureService {

    public VoitureDTO save(VoitureDTO absenceDTO);
    public VoitureDTO findById(Long id);
    public List<VoitureDTO> findAll();
    public void delete(Long id);
    public List<VoitureDTO> search(VoitureDTO absenceDTO);
}
