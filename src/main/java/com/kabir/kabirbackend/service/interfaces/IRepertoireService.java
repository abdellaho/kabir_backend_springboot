package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.RepertoireDTO;

import java.util.List;

public interface IRepertoireService {

    public RepertoireDTO save(RepertoireDTO repertoireDTO);
    public RepertoireDTO findById(Long id);
    public List<RepertoireDTO> findAll();
    public void delete(Long id);
    public List<RepertoireDTO> search(RepertoireDTO repertoireDTO);

    List<RepertoireDTO> exist(RepertoireDTO repertoireDTO);
}
