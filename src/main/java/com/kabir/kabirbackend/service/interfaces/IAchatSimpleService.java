package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.responses.AchatSimpleResponse;
import com.kabir.kabirbackend.dto.AchatSimpleDTO;

import java.util.List;

public interface IAchatSimpleService {
    public AchatSimpleDTO save(AchatSimpleResponse achatSimpleResponse);
    public AchatSimpleDTO findById(Long id);
    public AchatSimpleResponse findByIdAchatSimpleResponse(Long id);
    public List<AchatSimpleDTO> findAll();
    public void delete(Long id);
    public List<AchatSimpleDTO> search(AchatSimpleDTO stockDepot);
}
