package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.PersonnelDTO;

import java.util.List;

public interface IPersonnelService {

    public PersonnelDTO save(PersonnelDTO personnelDTO);
    public PersonnelDTO findById(Long id);
    public List<PersonnelDTO> findAll();
    public void delete(Long id);
    public List<PersonnelDTO> search(PersonnelDTO personnelDTO);
}
