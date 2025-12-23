package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.VilleDTO;

import java.util.List;

public interface IVilleService {

    public VilleDTO save(VilleDTO absenceDTO);
    public VilleDTO findById(Long id);
    public List<VilleDTO> findAll();
    public void delete(Long id);
    public List<VilleDTO> search(VilleDTO absenceDTO);
}
