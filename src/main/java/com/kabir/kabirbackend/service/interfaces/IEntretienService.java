package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.EntretienDTO;

import java.util.List;

public interface IEntretienService {
    EntretienDTO save(EntretienDTO entretienDTO);

    List<EntretienDTO> findAll();

    EntretienDTO findById(Long id);

    void delete(Long id);

    List<EntretienDTO> search(CommonSearchModel commonSearchModel);
}
