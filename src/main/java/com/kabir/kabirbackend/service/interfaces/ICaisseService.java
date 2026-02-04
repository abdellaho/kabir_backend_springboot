package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.CaisseDTO;

import java.util.List;

public interface ICaisseService {
    CaisseDTO save(CaisseDTO caisseDTO);

    CaisseDTO findById(Long id);

    List<CaisseDTO> findAll();

    void delete(Long id);

    List<CaisseDTO> search(CaisseDTO caisseDTO);

    List<CaisseDTO> searchByCommon(CommonSearchModel commonSearchModel);
}
