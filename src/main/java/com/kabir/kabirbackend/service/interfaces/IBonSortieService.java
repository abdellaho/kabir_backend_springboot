package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.responses.BonSortieResponse;
import com.kabir.kabirbackend.dto.BonSortieDTO;

import java.util.List;

public interface IBonSortieService {

    public BonSortieDTO save(BonSortieResponse BonSortieResponse);
    public BonSortieDTO findById(Long id);
    public BonSortieResponse findByIdBonSortieResponse(Long id);
    public List<BonSortieDTO> findAll();
    public void delete(Long id);
    public List<BonSortieDTO> search(BonSortieDTO BonSortie);
    int getLastNumBonSortie(BonSortieDTO bonSortieDTO);
}
