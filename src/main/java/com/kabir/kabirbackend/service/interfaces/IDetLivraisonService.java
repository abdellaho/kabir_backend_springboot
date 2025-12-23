package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.DetLivraisonDTO;

import java.util.List;

public interface IDetLivraisonService {

    public DetLivraisonDTO save(DetLivraisonDTO detLivraisonDTO);
    public DetLivraisonDTO findById(Long id);
    public List<DetLivraisonDTO> findAll();
    public void delete(Long id);
    public List<DetLivraisonDTO> search(DetLivraisonDTO detLivraisonDTO);
}
