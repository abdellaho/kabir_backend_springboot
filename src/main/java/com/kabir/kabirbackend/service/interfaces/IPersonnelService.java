package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.AbsenceDTO;
import com.kabir.kabirbackend.dto.PersonnelDTO;

import java.util.List;

public interface IPersonnelService {

    long count();

    PersonnelDTO save(PersonnelDTO personnelDTO);

    List<PersonnelDTO> findAllExceptAdmin(PersonnelDTO personnelDTO);

    PersonnelDTO findById(Long id);
    List<PersonnelDTO> findAll();

    PersonnelDTO findByEmail(String email);

    void delete(Long id);
    List<PersonnelDTO> search(PersonnelDTO personnelDTO);

    List<PersonnelDTO> notInAbsenceAtDate(AbsenceDTO absenceDTO);

    List<PersonnelDTO> searchBySupprimerOrArchiver(PersonnelDTO personnelDTO);
}
