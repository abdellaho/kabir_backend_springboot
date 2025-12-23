package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.AchatFactureDTO;

import java.util.List;

public interface IAchatFactureService {

    public AchatFactureDTO save(AchatFactureDTO achatFactureDTO);
    public AchatFactureDTO findById(Long id);
    public List<AchatFactureDTO> findAll();
    public void delete(Long id);
    public List<AchatFactureDTO> search(AchatFactureDTO achatFactureDTO);
}
