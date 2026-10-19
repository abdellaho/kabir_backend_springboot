package com.kabir.kabirbackend.service.interfaces;

import com.kabir.kabirbackend.config.requests.FacturePrintRequest;
import com.kabir.kabirbackend.config.responses.FactureResponse;
import com.kabir.kabirbackend.dto.FactureDTO;

import java.util.List;

public interface IFactureService {

    public FactureDTO save(FactureResponse factureResponse);
    public FactureDTO findById(Long id);
    public FactureResponse findByIdFactureResponse(Long id);
    public List<FactureDTO> findAll();
    public void delete(Long id);
    public List<FactureDTO> search(FactureDTO absenceDTO);
    int getLastNumFacture(FactureDTO factureDTO);

    /**
     * Prints one facture (the legacy row action {@code recupperer(3)} -> {@code BonFactr.jrxml})
     * and returns the PDF bytes. A missing / zero id or an empty detail list produces the legacy
     * one-page "aucun element" PDF.
     */
    byte[] imprimerFacture(Long id) throws Exception;

    /**
     * Prints one of the legacy "Consulter" etats of the facture screen and returns the PDF bytes.
     * See {@link FacturePrintRequest} for the supported {@code type} values.
     */
    byte[] imprimerEtat(FacturePrintRequest request) throws Exception;
}
