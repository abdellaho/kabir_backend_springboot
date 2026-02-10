package com.kabir.kabirbackend.service;


import com.kabir.kabirbackend.config.enums.ReportTypeEnum;
import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.config.util.JasperReportsUtil;
import com.kabir.kabirbackend.config.util.StaticVariables;
import com.kabir.kabirbackend.dto.RepertoireDTO;
import com.kabir.kabirbackend.entities.Personnel;
import com.kabir.kabirbackend.entities.Repertoire;
import com.kabir.kabirbackend.entities.Ville;
import com.kabir.kabirbackend.mapper.RepertoireMapper;
import com.kabir.kabirbackend.repository.LivraisonRepository;
import com.kabir.kabirbackend.repository.PersonnelRepository;
import com.kabir.kabirbackend.repository.RepertoireRepository;
import com.kabir.kabirbackend.repository.VilleRepository;
import com.kabir.kabirbackend.service.interfaces.IRepertoireService;
import com.kabir.kabirbackend.specifications.RepertoireSpecification;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RepertoireService implements IRepertoireService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final RepertoireRepository repertoireRepository;
    private final VilleRepository villeRepository;
    private final PersonnelRepository personnelRepository;
    private final RepertoireMapper repertoireMapper;
    private final LivraisonRepository livraisonRepository;
    private final JasperReportsUtil jasperReportsUtil;

    public RepertoireService(RepertoireRepository repertoireRepository,
                             RepertoireMapper repertoireMapper,
                             VilleRepository villeRepository,
                             PersonnelRepository personnelRepository,
                             LivraisonRepository livraisonRepository,
                             JasperReportsUtil jasperReportsUtil
    ) {
        this.repertoireRepository = repertoireRepository;
        this.villeRepository = villeRepository;
        this.personnelRepository = personnelRepository;
        this.repertoireMapper = repertoireMapper;
        this.livraisonRepository = livraisonRepository;
        this.jasperReportsUtil = jasperReportsUtil;
    }

    @Override
    public RepertoireDTO save(RepertoireDTO repertoireDTO) {
        logger.info("Saving repertoire: {}", repertoireDTO);
        try {
            Optional<Ville> villeOptional = villeRepository.findById(repertoireDTO.getVilleId());
            if (villeOptional.isEmpty()) {
                throw new RuntimeException("Ville not found");
            }

            Optional<Personnel> personnelOptional = null != repertoireDTO.getPersonnelId() ? personnelRepository.findById(repertoireDTO.getPersonnelId()) : Optional.empty();
            Repertoire repertoire = repertoireMapper.toRepertoire(repertoireDTO);
            repertoire.setVille(villeOptional.get());
            repertoire.setPersonnel(personnelOptional.orElse(null));
            repertoire = repertoireRepository.save(repertoire);
            return repertoireMapper.toRepertoireDTO(repertoire);
        } catch (Exception e) {
            logger.error("Error saving repertoire", e);
            throw new RuntimeException("Error saving repertoire", e);
        }
    }

    @Override
    public List<RepertoireDTO> findAll() {
        logger.info("Finding all repertoires");
        try {
            List<Repertoire> repertoires = repertoireRepository.findAll(Sort.by(Sort.Direction.ASC, "designation"));
            return repertoires.stream().map(repertoireMapper::toRepertoireDTO).toList();
        } catch (Exception e) {
            logger.error("Error finding all repertoires", e);
            throw new RuntimeException("Error finding all repertoires", e);
        }
    }

    @Override
    public RepertoireDTO findById(Long id) {
        logger.info("Finding repertoire by id: {}", id);
        try {
            Repertoire repertoire = repertoireRepository.findById(id).orElseThrow(() -> new RuntimeException("Repertoire not found"));
            return repertoireMapper.toRepertoireDTO(repertoire);
        } catch (Exception e) {
            logger.error("Error finding repertoire by id", e);
            throw new RuntimeException("Error finding repertoire by id", e);
        }
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting repertoire: {}", id);
        try {
            repertoireRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting repertoire", e);
            throw new RuntimeException("Error deleting repertoire", e);
        }
    }

    @Override
    public List<RepertoireDTO> search(RepertoireDTO repertoireDTO) {
        return repertoireRepository.findAll(RepertoireSpecification.searchBySupprimerOrArchiver(repertoireDTO)).stream().map(repertoireMapper::toRepertoireDTO).toList();
    }

    @Override
    public List<RepertoireDTO> searchClients(RepertoireDTO repertoireDTO) {
        return repertoireRepository.findAll(RepertoireSpecification.searchBySupprimerOrArchiverAndClientsOnly(repertoireDTO)).stream().map(repertoireMapper::toRepertoireDTO).toList();
    }

    @Override
    public List<RepertoireDTO> exist(RepertoireDTO repertoireDTO) {
        return repertoireRepository.findAll(RepertoireSpecification.builder().repertoireDTO(repertoireDTO).build()).stream().map(repertoireMapper::toRepertoireDTO).toList();
    }

    @Override
    public RepertoireDTO updateNbrOperation(Long id, Integer type) {
        if(null != id && null != type) {
            RepertoireDTO repertoireDTO = this.findById(id);

            if(null != repertoireDTO) {
                int numberToAdd = type == 1 ? 1 : -1;
                repertoireDTO.setNbrOperationClient(repertoireDTO.getNbrOperationClient() + numberToAdd);
                return this.save(repertoireDTO);
            } else {
                logger.error("Fournisseur not found with id: {}", id);
                throw new RuntimeException("Fournisseur not found with id: " + id);
            }
        }

        return null;
    }

    public byte[] imprimer(CommonSearchModel commonSearchModel, String etatPrint) throws Exception {
        List<RepertoireDTO> listRepertoires = new ArrayList<>(repertoireRepository.findAll(RepertoireSpecification.searchToPrint(commonSearchModel)).stream().map(repertoireMapper::toRepertoireDTO).toList());

        if(CollectionUtils.isEmpty(listRepertoires)) {
            return JasperReportsUtil.anullerImpr(StaticVariables.bundleFR.getString("aucuneResultatTrouve"));
        }

        StringBuilder villeName = new StringBuilder(), typeImprimer = new StringBuilder();
        boolean villeSelected = false;
        boolean avecCommercial = false;

        if(null != commonSearchModel.getVilleId() && commonSearchModel.getVilleId() > 0) {
            villeName.append("Ville : ").append(listRepertoires.getFirst().getVilleNomVille());
            villeSelected = true;
        }

        if(null != commonSearchModel.getPersonnelId() && commonSearchModel.getPersonnelId() > 0) {
            avecCommercial = true;
        }
        Comparator<RepertoireDTO> comparator = Comparator.nullsLast(Comparator.comparing(RepertoireDTO::getDateDernierBL, Comparator.nullsLast(Comparator.reverseOrder())));

        List<Long> ids = listRepertoires.stream().map(RepertoireDTO::getId).toList();
        List<Object[]> rows = livraisonRepository.findLastDatesForRepertoires(ids);
        Map<Long, LocalDate> map = rows.stream().collect(Collectors.toMap(r -> (Long) r[0], r -> (LocalDate) r[1]));
        listRepertoires.forEach(r ->r.setDateDernierBL(map.get(r.getId())));

        if(Integer.valueOf(0).equals(commonSearchModel.getTypeImprimRepertoire()) && !villeSelected) {
            comparator = Comparator.nullsLast(Comparator.comparing(RepertoireDTO::getDateDernierBL));
        }

        listRepertoires.sort(comparator);

        if(commonSearchModel.getTypeImprimRepertoire() != 0) {
            if(commonSearchModel.getTypeImprimRepertoire() == 1) {
                typeImprimer.append(StaticVariables.bundleFR.getString("blEgalZero"));
            }else if(commonSearchModel.getTypeImprimRepertoire() == 2) {
                typeImprimer.append(StaticVariables.bundleFR.getString("blEgalUn"));
            }else if(commonSearchModel.getTypeImprimRepertoire() == 3) {
                typeImprimer.append(StaticVariables.bundleFR.getString("blSuppDeUn"));
            }else if(commonSearchModel.getTypeImprimRepertoire() == 4) {
                typeImprimer.append(StaticVariables.bundleFR.getString("blInferieurOneYear"));
            }else if(commonSearchModel.getTypeImprimRepertoire() == 5) {
                typeImprimer.append(StaticVariables.bundleFR.getString("blSuppSixMonth"));
            }else if(commonSearchModel.getTypeImprimRepertoire() == 6) {
                typeImprimer.append(StaticVariables.bundleFR.getString("blInFerieurSixMonth"));
            }else if(commonSearchModel.getTypeImprimRepertoire() == 7) {
                typeImprimer.append(StaticVariables.bundleFR.getString("blSuppOneYear"));
            }
        }

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("villeName", villeName.toString());
        parameters.put("fichier", StaticVariables.bundleFR.getBaseBundleName());
        parameters.put("avecCommercial", avecCommercial ? "1" : "0");
        parameters.put("showDate", "1");
        parameters.put("type", commonSearchModel.getTypeImprimRepertoire() + "");
        parameters.put("typeImprimer", typeImprimer.toString());
        parameters.put("typeRepertoire", commonSearchModel.getTypeRepertoire() + "");

        byte[] bytes = jasperReportsUtil.jasperReportInBytes(listRepertoires, parameters, etatPrint, ReportTypeEnum.PDF, "");
        return bytes;
    }
}
