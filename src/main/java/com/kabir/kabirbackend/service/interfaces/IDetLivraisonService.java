package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.entities.Livraison;

import java.util.List;

public interface IDetLivraisonService {

    DetLivraisonDTO saveWithoutLivraison(DetLivraisonDTO detLivraisonDTO);

    public DetLivraisonDTO save(DetLivraisonDTO detLivraisonDTO, Livraison livraison);
    public DetLivraisonDTO findById(Long id);
    public List<DetLivraisonDTO> findAll();
    public void delete(Long id);
    public List<DetLivraisonDTO> DetLivraisonByLivraisonId(Long idLivraison);
    public List<DetLivraisonDTO> search(DetLivraisonDTO detLivraisonDTO);
}
