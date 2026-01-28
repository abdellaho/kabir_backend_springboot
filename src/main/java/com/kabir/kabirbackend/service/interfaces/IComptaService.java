package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.searchEntities.ComptaSearch;
import com.kabir.kabirbackend.dto.ComptaDTO;

import java.util.List;

public interface IComptaService {
    ComptaDTO save(ComptaDTO comptaDTO);

    ComptaDTO findById(Long id);

    List<ComptaDTO> findAll();

    void delete(Long id);

    List<ComptaDTO> search(ComptaSearch comptaSearch);
}
