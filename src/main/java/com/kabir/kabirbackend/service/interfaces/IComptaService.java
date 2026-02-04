package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.requests.ComptaRequest;
import com.kabir.kabirbackend.config.responses.ComptaResponse;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.searchEntities.ComptaSearch;
import com.kabir.kabirbackend.dto.ComptaDTO;

import java.util.List;

public interface IComptaService {
    ComptaDTO save(ComptaDTO comptaDTO);

    ComptaDTO findById(Long id);

    List<ComptaDTO> findAll();

    void delete(Long id);

    List<ComptaDTO> search(ComptaSearch comptaSearch);

    ComptaDTO getLastCompta();

    ComptaResponse getGlobalSums(ComptaRequest comptaRequest);

    boolean checkIsLast(ComptaSearch comptaSearch);

    List<ComptaDTO> searchByCommon(CommonSearchModel commonSearchModel);
}
