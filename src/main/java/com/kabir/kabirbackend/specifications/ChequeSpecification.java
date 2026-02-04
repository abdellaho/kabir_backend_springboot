package com.kabir.kabirbackend.specifications;


import com.kabir.kabirbackend.config.searchEntities.CommonSearchModel;
import com.kabir.kabirbackend.dto.ChequeDTO;
import com.kabir.kabirbackend.entities.Caisse;
import com.kabir.kabirbackend.entities.Cheque;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

@Data
@Builder
public class ChequeSpecification implements Specification<Cheque> {

    public Specification<Cheque> searchByCommon(CommonSearchModel commonSearchModel) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if(commonSearchModel.isSearchByDate()) {
                if(null != commonSearchModel.getDateDebut() && null != commonSearchModel.getDateFin()) {
                    if(commonSearchModel.getDateDebut().equals(commonSearchModel.getDateFin())) {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateCheque"), commonSearchModel.getDateDebut()));
                    } else {
                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateCheque"), commonSearchModel.getDateDebut(), commonSearchModel.getDateFin()));
                    }
                } else {
                    if(null != commonSearchModel.getDateDebut() || null != commonSearchModel.getDateFin()) {
                        if(null != commonSearchModel.getDateDebut()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("dateCheque"), commonSearchModel.getDateDebut()));
                        }
                        if(null != commonSearchModel.getDateFin()) {
                            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(root.get("dateCheque"), commonSearchModel.getDateFin()));
                        }
                    } else {
                        LocalDate dateDebut = LocalDate.now().minusYears(1);
                        LocalDate dateFin = LocalDate.now();

                        predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(root.get("dateCheque"), dateDebut, dateFin));
                    }
                }
            }

            if(null != commonSearchModel.getFournisseurId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("fournisseur").get("id"), commonSearchModel.getFournisseurId()));
            }
            if(null != commonSearchModel.getOperateurId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("operateur").get("id"), commonSearchModel.getOperateurId()));
            }

            if(commonSearchModel.getEtatcheque() != 2) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("etatcheque"), commonSearchModel.getEtatcheque()));
                if(commonSearchModel.getEtatcheque() == 1) {
                    query.orderBy(criteriaBuilder.desc(root.get("etatcheque")), criteriaBuilder.asc(root.get("etatcheque")));
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get("etatcheque")), criteriaBuilder.asc(root.get("etatcheque")));
                }

            } else {
                query.orderBy(criteriaBuilder.asc(root.get("etatcheque")), criteriaBuilder.asc(root.get("etatcheque")));
            }

            return predicate;
        };
    }

    private ChequeDTO chequeDTO;

    @Override
    public Predicate toPredicate(Root<Cheque> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        Predicate predicate = criteriaBuilder.conjunction();

        if (chequeDTO != null) {
            if (chequeDTO.getDateCheque() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("dateCheque"), chequeDTO.getDateCheque()));
            }

            if(chequeDTO.getCodeCheque() != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("codeCheque"), chequeDTO.getCodeCheque()));
            }

            if(null != chequeDTO.getId()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.notEqual(root.get("id"), chequeDTO.getId()));
            }
        }

        return predicate;
    }
}
