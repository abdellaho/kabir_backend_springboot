package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.AbsenceDTO;

import java.util.List;

public interface IAbsenceService {

    AbsenceDTO save(AbsenceDTO absenceDTO);
    AbsenceDTO findById(Long id);
    List<AbsenceDTO> findAll();
    void delete(Long id);
    List<AbsenceDTO> search(AbsenceDTO absenceDTO);

    boolean checkIfExist(AbsenceDTO absenceDTO);

    List<AbsenceDTO> searchByCommon(CommonSearchModel commonSearchModel);
}
