package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.EtablissementDTO;

import java.util.List;

public interface IEtablissementService {

    public EtablissementDTO save(EtablissementDTO absenceDTO);
    public EtablissementDTO findById(Long id);
    public List<EtablissementDTO> findAll();
    public void delete(Long id);
    public List<EtablissementDTO> search(EtablissementDTO absenceDTO);
}
