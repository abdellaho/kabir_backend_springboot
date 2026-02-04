package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.searchEntities.CompteCaisseSearch;
import com.kabir.kabirbackend.dto.CompteCaisseDTO;

import java.util.List;

public interface ICompteCaisseService {
    CompteCaisseDTO save(CompteCaisseDTO compteCaisseDTO);
    CompteCaisseDTO findById(Long id);
    List<CompteCaisseDTO> findAll();
    void delete(Long id);
    List<CompteCaisseDTO> search(CompteCaisseSearch compteCaisseSearch);

    List<CompteCaisseDTO> searchByCommon(CommonSearchModel commonSearchModel);
}
