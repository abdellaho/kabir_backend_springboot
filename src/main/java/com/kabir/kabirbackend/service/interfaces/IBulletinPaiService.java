package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.requests.BulletinPaiRequest;
import com.kabir.kabirbackend.config.responses.BulletinPaiResponse;
import com.kabir.kabirbackend.dto.BulletinPaiDTO;

import java.util.List;

public interface IBulletinPaiService {
    List<BulletinPaiDTO> findAll();

    BulletinPaiDTO findById(Long id);

    BulletinPaiResponse findByIdBulletinPaiResponse(Long id);

    BulletinPaiDTO save(BulletinPaiResponse bulletinPaiResponse);

    void delete(Long id);

    boolean searchIfExist(BulletinPaiDTO bulletinPaiDTO);

    List<BulletinPaiDTO> search(BulletinPaiDTO bulletinPaiDTO);

    int getLast();

    BulletinPaiResponse getDetails(BulletinPaiRequest bulletinPaiRequest);

    BulletinPaiResponse getDetailsOfLivraison(BulletinPaiRequest bulletinPaiRequest);
}