package com.kabir.kabirbackend.mapper;

import com.kabir.kabirbackend.dto.DetLivraisonDTO;
import com.kabir.kabirbackend.entities.DetLivraison;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Regression tests for the DTO -&gt; entity direction. The old implicit
 * ModelMapper mapping sent {@code stockQteFacturer} into
 * {@code livraison.facturer} (Integer -&gt; boolean); 86 threw a
 * MappingException and 0/1 silently corrupted the flag.
 * Plain JUnit: no Spring context and no database.
 */
class DetLivraisonMapperTest {

    private final DetLivraisonMapper mapper = new DetLivraisonMapper(new ModelMapper());

    private static DetLivraisonDTO dto(int stockQteFacturer) {
        DetLivraisonDTO dto = new DetLivraisonDTO();
        dto.setId(19L);
        dto.setQteLivrer(4);
        dto.setChampsRouge(1);
        dto.setRemiseLivraison(5.5);
        dto.setPrixVente(120.0);
        dto.setMontantProduit(480.0);
        dto.setBeneficeDH(30.0);
        dto.setBenepourcentage(6.25);
        dto.setInfinity(0);
        dto.setAvecRemise(false);
        dto.setLivraisonId(19L);
        dto.setStockId(1L);
        dto.setStockQteStock(101);
        dto.setStockQteFacturer(stockQteFacturer);
        dto.setStockPvttc(120.0);
        return dto;
    }

    private static void assertMappedScalars(DetLivraison entity) {
        assertEquals(19L, entity.getId());
        assertEquals(4, entity.getQteLivrer());
        assertEquals(1, entity.getChampsRouge());
        assertEquals(5.5, entity.getRemiseLivraison(), 0.000001);
        assertEquals(120.0, entity.getPrixVente(), 0.000001);
        assertEquals(480.0, entity.getMontantProduit(), 0.000001);
        assertEquals(30.0, entity.getBeneficeDH(), 0.000001);
        assertEquals(6.25, entity.getBenepourcentage(), 0.000001);
        assertEquals(0, entity.getInfinity());
        assertFalse(entity.isAvecRemise());
    }

    @Test
    void mapsStockQteFacturer86WithoutBooleanConversion() {
        DetLivraison entity = assertDoesNotThrow(() -> mapper.toDetLivraison(dto(86)));

        assertMappedScalars(entity);
        assertNotNull(entity.getLivraison());
        assertEquals(19L, entity.getLivraison().getId());
        assertFalse(entity.getLivraison().isFacturer());
        assertNull(entity.getStock());
    }

    @Test
    void mapsStockQteFacturer1WithoutSilentFacturerCorruption() {
        DetLivraison entity = assertDoesNotThrow(() -> mapper.toDetLivraison(dto(1)));

        assertMappedScalars(entity);
        assertNotNull(entity.getLivraison());
        assertEquals(19L, entity.getLivraison().getId());
        assertFalse(entity.getLivraison().isFacturer());
        assertNull(entity.getStock());
    }
}
