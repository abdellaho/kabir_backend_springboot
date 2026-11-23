package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.DetFactureDTO;
import com.kabir.kabirbackend.entities.DetFacture;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Guard for the explicit DTO -&gt; entity mapping. DetFacture never had the
 * Integer -&gt; boolean path (Facture has facturer100, not facturer), so this
 * test passed before the fix too; it keeps the scalar-only mapping in place.
 * Plain JUnit: no Spring context and no database.
 */
class DetFactureMapperTest {

    private final DetFactureMapper mapper = new DetFactureMapper(new ModelMapper());

    private static DetFactureDTO dto() {
        DetFactureDTO dto = new DetFactureDTO();
        dto.setId(30L);
        dto.setQteFacturer(4);
        dto.setRemiseFacture(5.5);
        dto.setPrixVente(120.0);
        dto.setMontantProduit(480.0);
        dto.setBeneficeDH(30.0);
        dto.setBenepourcentage(6.25);
        dto.setTva7(7.7);
        dto.setTva20(20.2);
        dto.setMontantProduitHT(400.0);
        dto.setDirectFacture(1);
        dto.setAvecRemise(false);
        dto.setFactureId(25L);
        dto.setStockId(1L);
        dto.setStockQteFacturer(86);
        dto.setFactureFacturer100(false);
        return dto;
    }

    @Test
    void mapsScalarsOnlyWithoutFactureOrStock() {
        DetFacture entity = assertDoesNotThrow(() -> mapper.toEntity(dto()));

        assertEquals(30L, entity.getId());
        assertEquals(4, entity.getQteFacturer());
        assertEquals(5.5, entity.getRemiseFacture(), 0.000001);
        assertEquals(120.0, entity.getPrixVente(), 0.000001);
        assertEquals(480.0, entity.getMontantProduit(), 0.000001);
        assertEquals(30.0, entity.getBeneficeDH(), 0.000001);
        assertEquals(6.25, entity.getBenepourcentage(), 0.000001);
        assertEquals(7.7, entity.getTva7(), 0.000001);
        assertEquals(20.2, entity.getTva20(), 0.000001);
        assertEquals(400.0, entity.getMontantProduitHT(), 0.000001);
        assertEquals(1, entity.getDirectFacture());
        assertFalse(entity.isAvecRemise());
        assertNull(entity.getFacture());
        assertNull(entity.getStock());
    }
}
