package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.FournisseurDTO;

import java.util.List;

public interface IFournisseurService {

    public FournisseurDTO save(FournisseurDTO fournisseurDTO);
    public FournisseurDTO findById(Long id);
    public List<FournisseurDTO> findAll();
    public void delete(Long id);
    public List<FournisseurDTO> search(FournisseurDTO fournisseurDTO);
}
