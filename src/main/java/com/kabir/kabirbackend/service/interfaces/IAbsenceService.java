package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.AbsenceDTO;

import java.util.List;

public interface IAbsenceService {

    public AbsenceDTO save(AbsenceDTO absenceDTO);
    public AbsenceDTO findById(Long id);
    public List<AbsenceDTO> findAll();
    public void delete(Long id);
    public List<AbsenceDTO> search(AbsenceDTO absenceDTO);
}
