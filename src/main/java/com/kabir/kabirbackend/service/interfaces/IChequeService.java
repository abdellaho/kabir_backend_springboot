package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.dto.ChequeDTO;

import java.util.List;

public interface IChequeService {
    ChequeDTO save(ChequeDTO chequeDTO);

    ChequeDTO findById(Long id);

    List<ChequeDTO> findAll();

    void delete(Long id);

    List<ChequeDTO> search(ChequeDTO chequeDTO);

    int getLastNum();
}
