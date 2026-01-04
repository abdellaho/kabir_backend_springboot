package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.responses.LivraisonResponse;
import com.kabir.kabirbackend.dto.LivraisonDTO;

import java.util.List;

public interface ILivraisonService {

    public LivraisonDTO save(LivraisonResponse livraisonResponse);
    public LivraisonDTO findById(Long id);

    LivraisonResponse findByIdWithDetLivraison(Long id);

    public List<LivraisonDTO> findAll();
    public void delete(Long id);
    public List<LivraisonDTO> search(LivraisonDTO livraisonDTO);
    public int getLastNumLivraison(LivraisonDTO livraisonDTO);
}
