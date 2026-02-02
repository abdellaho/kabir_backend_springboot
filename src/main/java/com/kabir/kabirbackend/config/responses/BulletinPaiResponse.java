package com.kabir.kabirbackend.config.responses;

import com.kabir.kabirbackend.dto.BulletinPaiDTO;
import com.kabir.kabirbackend.dto.DetBulletinLivraisonDTO;
import com.kabir.kabirbackend.dto.DetBulletinPaiDTO;

import java.util.List;

public record BulletinPaiResponse(
        BulletinPaiDTO bulletinPai,
        List<DetBulletinPaiDTO> detBulletinPais,
        List<DetBulletinPaiDTO> detBulletinPaisSansMontant,
        List<DetBulletinLivraisonDTO> detBulletinLivraisons
) {
}