package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.responses.ValidationResponse;
import com.kabir.kabirbackend.dto.RepertoireDTO;

import java.util.List;

public interface IRepertoireService {

    RepertoireDTO save(RepertoireDTO repertoireDTO);
    RepertoireDTO findById(Long id);
    List<RepertoireDTO> findAll();
    void delete(Long id);
    List<RepertoireDTO> search(RepertoireDTO repertoireDTO);

    List<RepertoireDTO> searchClients(RepertoireDTO repertoireDTO);

    List<RepertoireDTO> exist(RepertoireDTO dto);

    RepertoireDTO updateNbrOperation(Long id, Integer type);

    ValidationResponse existingTest(RepertoireDTO dto);
}
