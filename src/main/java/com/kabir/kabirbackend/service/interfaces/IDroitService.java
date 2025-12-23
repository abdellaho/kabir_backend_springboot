package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.DroitDTO;

import java.util.List;

public interface IDroitService {

    public DroitDTO save(DroitDTO droitDTO);
    public DroitDTO findById(Long id);
    public List<DroitDTO> findAll();
    public void delete(Long id);
    public List<DroitDTO> search(DroitDTO droitDTO);
}
