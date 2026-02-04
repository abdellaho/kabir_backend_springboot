package com.kabir.kabirbackend.specifications;

import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.FactureDTO;
import com.kabir.kabirbackend.entities.Compta;
import com.kabir.kabirbackend.entities.Facture;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;


@Data
@Builder
public class FactureSpecification implements Specification<Facture> {

    public Specification<Facture> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateBF"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateBF"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut() || null != commonSearchModel.getDateFin()) {
                        if(null != commonSearchModel.getDateDebut()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateBF"), commonSearchModel.getDateDebut()));
                        }
                        if(null != commonSearchModel.getDateFin()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateBF"), commonSearchModel.getDateFin()));
                        }
                    } else {
                        LocalDate dateDebut = LocalDate.now().minusYears(1);
                        LocalDate dateFin = LocalDate.now();

                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateBF"), dateDebut, dateFin));
                    }
                }
            }

            if(StringUtils.isNotBlank(commonSearchModel.getNumCheque())) {
                Predicate predicateNumCheque = criteriaBuilder.like(criteriaBuilder.lower(root.get("numCheque")), "%" + commonSearchModel.getNumCheque().trim().toLowerCase() + "%");
                Predicate predicateNumCheque2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("numCheque2")), "%" + commonSearchModel.getNumCheque().trim().toLowerCase() + "%");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(predicateNumCheque, predicateNumCheque2));
            }

            if(StringUtils.isNotBlank(commonSearchModel.getNumRemise())) {
                Predicate predicateNumCheque = criteriaBuilder.like(criteriaBuilder.lower(root.get("numRemise")), "%" + commonSearchModel.getNumRemise().trim().toLowerCase() + "%");
                Predicate predicateNumCheque2 = criteriaBuilder.like(criteriaBuilder.lower(root.get("numRemise2")), "%" + commonSearchModel.getNumRemise().trim().toLowerCase() + "%");
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.or(predicateNumCheque, predicateNumCheque2));
            }

            if(null != commonSearchModel.getPersonnelId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("personnel").get("id"), commonSearchModel.getPersonnelId()));
            }
            if(null != commonSearchModel.getRepertoireId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("repertoire").get("id"), commonSearchModel.getRepertoireId()));
            }
            if(null != commonSearchModel.getOperateurId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("employeOperateur").get("id"), commonSearchModel.getOperateurId()));
            }

            query.orderBy(criteriaBuilder.desc(root.get("dateReglement")), criteriaBuilder.desc(root.get("numFacture")));

            return predicate;
        };
    }

    private FactureDTO factureDTO;

    @Override
    public Predicate toPredicate(Root<Facture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (factureDTO != null) {
            if (StringUtils.isNotBlank(factureDTO.getCodeBF())) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("codeBF"), factureDTO.getCodeBF()));
            }

            if(null != factureDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), factureDTO.getId()));
            }
        }
        return predicate;

    }
}
