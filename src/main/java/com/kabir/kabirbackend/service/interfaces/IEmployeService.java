package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.EmployeDTO;

import java.util.List;

public interface IEmployeService {

    public EmployeDTO save(EmployeDTO absenceDTO);
    public EmployeDTO findById(Long id);
    public List<EmployeDTO> findAll();
    public void delete(Long id);
    public List<EmployeDTO> search(EmployeDTO absenceDTO);
}
