package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.PrimeDTO;

import java.util.List;

public interface IPrimeService {

    public PrimeDTO save(PrimeDTO absenceDTO);
    public PrimeDTO findById(Long id);
    public List<PrimeDTO> findAll();
    public void delete(Long id);
    public List<PrimeDTO> search(PrimeDTO absenceDTO);
}
