package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.LivraisonDTO;

import java.util.List;

public interface ILivraisonService {

    public LivraisonDTO save(LivraisonDTO livraisonDTO);
    public LivraisonDTO findById(Long id);
    public List<LivraisonDTO> findAll();
    public void delete(Long id);
    public List<LivraisonDTO> search(LivraisonDTO livraisonDTO);
    public int getLastNumLivraison(LivraisonDTO livraisonDTO);
}
